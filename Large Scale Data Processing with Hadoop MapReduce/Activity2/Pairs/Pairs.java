import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Pairs {

	public static class PairsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] linetokens = value.toString().replaceAll(" +", " ").trim().split("\\s");
			if (linetokens.length > 1) {
				for (int i = 0; i < linetokens.length - 1; i++) {
					for (int j = i+1; j < linetokens.length; j++) {
						context.write(new Text(linetokens[i] + " " + linetokens[j]), one);
					}
				}
			}

		}
	}

	public static class PairsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int count = 0;
			for (IntWritable value : values) {
				count += value.get();
			}
			result.set(count);
			context.write(key, result);
		}

	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Pairs");
		job.setJarByClass(Pairs.class);
		job.setMapperClass(PairsMapper.class);
		job.setCombinerClass(PairsReducer.class);
		job.setReducerClass(PairsReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}