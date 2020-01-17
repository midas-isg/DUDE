import java.util.ArrayList ;

public class RunDUDWithHeuristic {

  static double MIN_THRESHOLD = -50.0 ;
  static double MAX_THRESHOLD = +50.0 ;
  static double INCREMENT     = 0.01 ;
  static int    MIN_BASELINE  = 50 ;
  static int    WAIT          = 14 ;
  static int    WINDOW        = 28 ;

  public static void main(String[] args) {
    Features features = new Features() ;
    String outbreakFile ;
    Data data ;
    double[] dailyLogOdds ;

    outbreakFile = "../Data/slc-2014-2015-unexplained-ili.data" ;
    data = new Data(features) ;
    data.load(outbreakFile) ;

    dailyLogOdds = DUD.logOddsOfUnmodeledSeparateSort(MIN_BASELINE, WAIT, WINDOW, data, features, 0.30) ;

    for (int day=0 ; day<dailyLogOdds.length ; day++) { System.out.println( day + " " + dailyLogOdds[day] ) ; }

  }

}
