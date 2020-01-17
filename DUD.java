import java.util.ArrayList ;

public class DUD {

  private static int    INCREMENT  = 1 ;
  private static double IMPOSSIBLE = -1000.0 ;

  public static double[] logOddsOfUnmodeledUnsorted(int minimumBaseline, int wait, int monitor, Data data, Features features) {
    Window baselineWindow, monitorWindow ;
    double[] result = new double[data.numberOfDays()] ;
    for (int c=0 ; c<(minimumBaseline+wait+monitor) ; c++) { result[c] = IMPOSSIBLE ; }
    for (int c=(minimumBaseline+wait+monitor) ; c<data.numberOfDays() ; c++) {
baselineWindow = new Window(0, c-(wait+monitor), data, features) ;
monitorWindow  = new Window(c-monitor, monitor, data, features) ;
      result[c] = oddsUnmodeled(baselineWindow, monitorWindow, features).log() ;
    }
    return result ;
  }

  public static double[] logOddsOfUnmodeledBaselineSort(int minimumBaseline, int wait, int monitor, Data data, Features features, double fraction) {
    Window calibrationWindow, baselineWindow, monitorWindow ;
    double[] result = new double[data.numberOfDays()] ;
    for (int c=0 ; c<(minimumBaseline+wait+monitor) ; c++) { result[c] = IMPOSSIBLE ; }
    for (int c=(minimumBaseline+wait+monitor) ; c<data.numberOfDays() ; c++) {
calibrationWindow = new Window(0, c-(wait+monitor), data, features) ;
baselineWindow    = new Window(0, c-(wait+monitor), data, features, calibrationWindow, fraction) ;
monitorWindow  = new Window(c-monitor, monitor, data, features, calibrationWindow, fraction) ;
      result[c] = oddsUnmodeled(baselineWindow, monitorWindow, features).log() ;
    }
    return result ;
  }

  public static double[] logOddsOfUnmodeledSeparateSort(int minimumBaseline, int wait, int monitor, Data data, Features features, double fraction) {
    Window calibrationWindow, baselineWindow, monitorWindow ;
    double[] result = new double[data.numberOfDays()] ;
    for (int c=0 ; c<(minimumBaseline+wait+monitor) ; c++) { result[c] = IMPOSSIBLE ; }
    for (int c=(minimumBaseline+wait+monitor) ; c<data.numberOfDays() ; c++) {
calibrationWindow = new Window(0, c-(wait+monitor), data, features) ;
baselineWindow    = new Window(0, c-(wait+monitor), data, features, calibrationWindow, fraction) ;
calibrationWindow = new Window(c-monitor, monitor, data, features) ;
monitorWindow     = new Window(c-monitor, monitor, data, features, calibrationWindow, fraction) ;
      result[c] = oddsUnmodeled(baselineWindow, monitorWindow, features).log() ;
    }
    return result ;
  }

  private static Precise likelihoodModeled(Window baselineWindow, Window monitorWindow, int feature) {
    double theta ;
    int k, N ;
    theta = baselineWindow.theta(feature) ;
    N = monitorWindow.total() ;
    k = monitorWindow.count(feature) ;
    return Precise.binomial(N,k,theta) ;
  }

  private static Precise likelihoodModeled(Window baselineWindow, Window monitorWindow, Features features) {
    Precise result = new Precise(1.0) ;
    for (int feature=0 ; feature<features.numberOfFeatures() ; feature++) {
      if ( !features.isLegal(feature) ) continue ;
      result = result.multiply( likelihoodModeled(baselineWindow,monitorWindow,feature) ) ;
    }
    return result ;
  }

  private static Precise probabilityOfFeatureCount(int k, int m, int u, double thetaM) {
    Precise result = new Precise(0.0), a, b ;
    for (int i=0 ; i<=k ; i++) {
      a = Precise.binomial(m,i,thetaM) ;
      if ( (k-i)<=u ) b = new Precise( 1.0 / (u+1.0) ) ; else b = new Precise(0.0) ;
      result = result.add( a.multiply(b) ) ;
    }
    return result ;
  }

  private static Precise likelihoodUnmodeled(Window baselineWindow, Window monitorWindow, int m, int u, Features features) {
    Precise result = new Precise(1.0) ;
    int k ;
    double thetaM ;
    for (int feature=0 ; feature<features.numberOfFeatures() ; feature++) {
      if ( !features.isLegal(feature) ) continue ;
      k = monitorWindow.count(feature) ;
      thetaM = baselineWindow.theta(feature) ;
      result = result.multiply( probabilityOfFeatureCount(k,m,u,thetaM) ) ;
    }
    return result ;
  }

  private static Precise likelihoodUnmodeled(Window baselineWindow, Window monitorWindow, Features features) {
    Precise current, result ;
    int intervals, m ;
    result = new Precise(0.0) ;
    intervals = 0 ;
    for (int u=1 ; u<=monitorWindow.total() ; u+=INCREMENT) {
      m = monitorWindow.total()-u ;
      current = likelihoodUnmodeled(baselineWindow, monitorWindow, m, u, features) ;
      result = result.add( current ) ;
      intervals++ ;
    }
    return result.divide(new Precise(intervals)) ;
  }

  private static Precise oddsUnmodeled(Window baselineWindow, Window monitorWindow, Features features) {
    Precise numerator = likelihoodUnmodeled(baselineWindow, monitorWindow, features) ;
    Precise denominator = likelihoodModeled(baselineWindow, monitorWindow, features) ;
    return numerator.divide(denominator) ;
  }

}
