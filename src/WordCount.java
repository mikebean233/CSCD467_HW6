import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {

  public static class TokenizerMapper extends Mapper<Object, Text, Text, Text>{
    
    private Text targetAndPath = new Text();
    private Text lineNo = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
      context.getFileClassPaths();

      StringTokenizer itr = new StringTokenizer(value.toString());
      String lineNoString = itr.nextToken();
      String path = ((FileSplit) context.getInputSplit()).getPath().toString();
      String target = context.getConfiguration().get("MyKey");
      String fileLength = "0+" + context.getInputSplit().getLength();
      int occurenceCount = 0;

      while (itr.hasMoreTokens()) {
        String thisToken = itr.nextToken();
        if(thisToken.toLowerCase().equals(target))
          occurenceCount++;
      }  
      if(occurenceCount !=0){
        targetAndPath.set(target + "\t" + path + ":" + fileLength + ", ");
        lineNo.set(lineNoString);
        context.write(targetAndPath, lineNo);  
      }
    }
  } // end TokenizeMapper
  
  public static class LameReducer extends Reducer<Text,Text,Text,Text> {
    private Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
      for (Text val : values) {
        result.set(val);
        context.write(key, result);
      }
    }
  } // end LameReducer

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

    if (otherArgs.length < 3) {
      System.err.println("Usage: wordcount <pattern> <in> [<in>...] <out>");
      System.exit(2);
    }

    String pattern = args[0];
    conf.set("MyKey", pattern);

    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(LameReducer.class);
    job.setReducerClass(LameReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    for (int i = 1; i < otherArgs.length - 1; ++i) {
      FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
    }

    FileOutputFormat.setOutputPath(job,
      new Path(otherArgs[otherArgs.length - 1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }// end main
}// end WordCount