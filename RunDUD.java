public class RunDUD {

  public static void main(String[] args) {
    int minimumBaseline, wait, window ;
    double fraction ;
    String dataFile ;
    Features features ;
    double[] logOddsOfUnmodeled ;
    features = new Features() ;
    Data data = new Data(features) ;
    
    minimumBaseline = Integer.parseInt( args[0] ) ;
    wait = Integer.parseInt( args[1] ) ;
    window = Integer.parseInt( args[2] ) ;
    data.load(args[3]) ;
    logOddsOfUnmodeled = DUD.logOddsOfUnmodeledUnsorted(minimumBaseline, wait, window, data, features) ;
    for (int d=0 ; d<data.numberOfDays() ; d++) { System.out.println( d + " " + logOddsOfUnmodeled[d] ) ; }
  }

}
