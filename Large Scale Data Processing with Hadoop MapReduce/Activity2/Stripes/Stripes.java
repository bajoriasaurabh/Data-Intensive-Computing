import java.io.IOException;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Stripes {

	public static class StripesMapper extends Mapper<LongWritable, Text, Text, MapWritableOverride> {

		private MapWritableOverride mapWritableOverride = new MapWritableOverride();
		private final static IntWritable one = new IntWritable(1);

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] tokens = value.toString().replaceAll(" +", " ").trim().split("\\s");

			if (tokens.length > 1) {
				for (int i = 0; i < tokens.length - 1; i++) {
					mapWritableOverride.clear();
					for (int j = i + 1; j < tokens.length; j++) {
						if (mapWritableOverride.containsKey(new Text(tokens[j]))) {
							IntWritable intWritable = (IntWritable) mapWritableOverride.get(new Text(tokens[j]));
							intWritable.set(intWritable.get() + 1);
						} else {
							mapWritableOverride.put(new Text(tokens[j]), one);
						}
						context.write(new Text(tokens[i]), mapWritableOverride);
					}
				}

			}
		}
	}

	public static class StripesReducer extends Reducer<Text, MapWritable, Text, Text> {
		private MapWritableOverride mapWritableOverride = new MapWritableOverride();

		public void reduce(Text key, Iterable<MapWritable> values, Context context)
				throws IOException, InterruptedException {
			mapWritableOverride.clear();
			for (MapWritable value : values) {
				Set<Writable> keySet = value.keySet();
				for (Writable keys : keySet) {
					IntWritable sum = (IntWritable) value.get(keys);
					if (mapWritableOverride.containsKey(keys)) {
						IntWritable intWritable = (IntWritable) mapWritableOverride.get(keys);
						intWritable.set(intWritable.get() + sum.get());
					} else {
						mapWritableOverride.put(keys, sum);
					}
				}

			}
			for (Object object : mapWritableOverride.keySet()) {
				context.write(key, new Text(object + "	" + mapWritableOverride.get(object)));
			}
		}
	}

	public static class MapWritableOverride extends MapWritable {
		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			Set<Writable> keySet = this.keySet();

			for (Object object : keySet) {
				stringBuilder.append("/n" + object.toString() + "/t" + this.get(object));
			}
			return stringBuilder.toString();
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Stripes");
		job.setJarByClass(Stripes.class);
		job.setMapperClass(StripesMapper.class);
		job.setReducerClass(StripesReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MapWritableOverride.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}