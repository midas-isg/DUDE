import java.util.ArrayList ;

public class AMOC {

  static double MIN_THRESHOLD = -50.0 ;
  static double MAX_THRESHOLD = +50.0 ;
  static double INCREMENT     = 0.01 ;
  static int    MIN_BASELINE  = 50 ;
  static int    WAIT          = 14 ;
  static int    WINDOW        = 28 ;

  public static void main(String[] args) {
    Features features = new Features() ;
    Data data ;
    int startDay, numberOfFalseAlarms ;
    double[] logOddsOfUnmodeled ;
    double fraction, daysToDetection ;
    ArrayList<double[]> logOddsOfUnmodeledArrays = new ArrayList<double[]>() ;
    ArrayList<Integer> startDays = new ArrayList<Integer>() ;
    for (String outbreakFile : outbreakFiles) {
      data = new Data(features) ;
      data.load(outbreakFile) ;
      startDays.add( StartDay.startDay(data) ) ;
      data.load(baselineILIFile) ;

      // Unsorted
      //fraction = -1 ;
      //logOddsOfUnmodeledArrays.add( DUD.logOddsOfUnmodeledUnsorted(MIN_BASELINE, WAIT, WINDOW, data, features) ) ;

      // Baseline sort
      //fraction = Double.parseDouble( args[0] ) ;
      //logOddsOfUnmodeledArrays.add( DUD.logOddsOfUnmodeledBaselineSort(MIN_BASELINE, WAIT, WINDOW, data, features, fraction) ) ;

      // Separate sort
      fraction = Double.parseDouble( args[0] ) ;
      logOddsOfUnmodeledArrays.add( DUD.logOddsOfUnmodeledSeparateSort(MIN_BASELINE, WAIT, WINDOW, data, features, fraction) ) ;

    }
    for (double threshold=MIN_THRESHOLD ; threshold<=MAX_THRESHOLD ; threshold+=INCREMENT) {
      numberOfFalseAlarms = 0 ;
      daysToDetection = 0.0 ;
      for (int n=0 ; n<logOddsOfUnmodeledArrays.size() ; n++) {
        startDay = startDays.get(n) ;
        logOddsOfUnmodeled = logOddsOfUnmodeledArrays.get(n) ;
        numberOfFalseAlarms += numberOfFalseAlarms(logOddsOfUnmodeled,startDay,threshold) ;
        daysToDetection += daysToDetection(logOddsOfUnmodeled,startDay,threshold) ;
      }
      System.out.println( (int)((double)numberOfFalseAlarms/(double)outbreakFiles.length) + " " +
                          (int)((double)daysToDetection/(double)outbreakFiles.length) + " " +
			  threshold
			) ;
    }
  }

  static int numberOfFalseAlarms(double[] logOddsOfUnmodeled, int startDay, double threshold) {
    int total = 0 ;
    for (int d=0 ; d<startDay ; d++) { if (logOddsOfUnmodeled[d]>=threshold) total++ ; }
    return total ;
  }

/*
  static int daysToDetection(double[] logOddsOfUnmodeled, int startDay, double threshold) {
    for (int d=startDay ; d<logOddsOfUnmodeled.length ; d++) {
      if ( logOddsOfUnmodeled[d]>=threshold ) return d-startDay ;
    }
    return logOddsOfUnmodeled.length ;
  }
*/

  static int daysToDetection(double[] logOddsOfUnmodeled, int startDay, double threshold) {
    for (int d=0 ; d<logOddsOfUnmodeled.length ; d++) {
      if ( logOddsOfUnmodeled[d]>=threshold && d<startDay ) return 0 ;
      if ( logOddsOfUnmodeled[d]>=threshold ) return d-startDay ;
    }
    return logOddsOfUnmodeled.length ;
  }

static String baselineILIFile = "../Data/slc-2011-2012-unexplained-ili.data" ;

  static String[] outbreakFiles = {
    "../Data/slc-2010-2011-confirmed-influenza.data",
    "../Data/slc-2011-2012-confirmed-influenza.data",
    "../Data/slc-2012-2013-confirmed-influenza.data",
    "../Data/slc-2013-2014-confirmed-influenza.data",
    "../Data/slc-2014-2015-confirmed-influenza.data",
    "../Data/slc-2010-2011-confirmed-rsv.data",
    "../Data/slc-2011-2012-confirmed-rsv.data",
    "../Data/slc-2012-2013-confirmed-rsv.data",
    "../Data/slc-2013-2014-confirmed-rsv.data",
    "../Data/slc-2014-2015-confirmed-rsv.data",
    "../Data/slc-2010-2011-confirmed-hmpv.data",
    "../Data/slc-2011-2012-confirmed-hmpv.data",
    "../Data/slc-2012-2013-confirmed-hmpv.data",
    "../Data/slc-2013-2014-confirmed-hmpv.data",
    "../Data/slc-2014-2015-confirmed-hmpv.data"
  } ;


}
