import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount_Latin {
	static HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

	public static class LatinMapper extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			if (value.toString().length() == 0) {
			} else {
				String value1 = value.toString().substring(value.toString().indexOf("<"), value.toString().indexOf(">")+1)
						.trim();
				String value2 = value.toString().substring(value.toString().indexOf(">"));
				value2 = value2.replaceAll("j", "i").replaceAll("v", "u").replaceAll("[^A-Za-z0-9\\s]", "").replaceAll(" +", " ").trim();
				String tokens[] = value2.split("\\s");
				for (int i = 1; i < tokens.length; i++) {
					context.write(new Text(tokens[i]), new Text(value1));
				}
			}
		}
	}

	public static class LatinCombiner extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			String temp = "";
			for (Text value : values) {
				temp += value.toString() + " ";
			}
			context.write(key, new Text(temp));
		}
	}

	public static class LatinReducer extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			String temp = "";
			for (Text value : values) {
				temp += value.toString() + " ";
			}
			int count = temp.length() - temp.replace("<", "").length();
			if (hm.containsKey(key.toString().toLowerCase())) {
				ArrayList<String> list = new ArrayList<String>();
				list = hm.get(key.toString().toLowerCase());
				for (int i = 0; i < list.size(); i++) {
					context.write(new Text(list.get(i)), new Text(temp + ", Count: " + Integer.toString(count)));
				}
			} else {
				context.write(key, new Text(temp + ", Count: " + Integer.toString(count)));
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Path pt = new Path("hdfs://localhost:9000//home//hadoop//newlemmatizer");
		FileSystem fs = FileSystem.get(new Configuration());
		BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
		String line;
		while ((line = br.readLine()) != null) {
			String tokens[] = line.split(",");
			if (!hm.containsKey(tokens[0].toLowerCase())) {
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < tokens.length; i++) {
					if (tokens[i] != null || tokens[i] != "") {
						list.add(tokens[i].toLowerCase());
					}
				}
				hm.put(tokens[0].toLowerCase(), list);
			}
		}
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "LatinWordCount");
		job.setJarByClass(WordCount_Latin.class);
		job.setMapperClass(LatinMapper.class);
		job.setCombinerClass(LatinCombiner.class);
		job.setReducerClass(LatinReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}