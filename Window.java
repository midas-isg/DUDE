import java.util.ArrayList ;
import java.util.Arrays ;

public class Window {

  public static double SMOOTHING = 1.0 ;

  private int start, window, total ;
  private Data data ;
  private Features features ;
  private int[] counts ;

  public Window(int start, int window, Data data, Features features) {
    this.start = start ;
    this.window = window ;
    this.data = data ;
    this.features = features ;
    this.counts = new int[features.numberOfFeatures()] ;
    this.total = data.numberOfPatients(start,window) ;
    for (int f=0 ; f<features.numberOfFeatures() ; f++) {
      counts[f] = data.numberOfPatientsWithFeature(start,window,f) ;
    }
  }

  public int numberOfFeatures() { return features.numberOfFeatures() ; }

  public int count(int feature) { return counts[feature] ; }

  public double theta(int feature) { return (counts[feature]+SMOOTHING)/(total+(2.0*SMOOTHING)) ; }

  public int total() { return total ; }

  public Window(int start, int window, Data data, Features features, Window calibrationWindow, double fraction) {
    this.start = start ;
    this.window = window ;
    this.data = data ;
    this.features = features ;
    this.counts = new int[features.numberOfFeatures()] ;
    this.total = data.numberOfPatients(start,window) ;
    ArrayList<Double> likelihoods = new ArrayList<Double>() ;
    for (int day=start ; day<(start+window) ; day++) {
      for (int patient=0 ; patient<data.numberOfPatients(day) ; patient++) {
        likelihoods.add( likelihood(data, day, patient,calibrationWindow) ) ;
      }
    }
    Double[] likelihoodsArray = new Double[ likelihoods.size() ] ;
    likelihoodsArray = likelihoods.toArray(likelihoodsArray) ;
    Arrays.sort( likelihoodsArray ) ;
    double cutoff = likelihoodsArray[ ((int)( fraction*likelihoodsArray.length )) - 1 ] ;
    for (int day=start ; day<(start+window) ; day++) {
      for (int patient=0 ; patient<data.numberOfPatients(day) ; patient++) {
        if ( likelihood(data, day, patient, calibrationWindow)<=cutoff ) {
          for (int feature=0 ; feature<features.numberOfFeatures() ; feature++) {
            if ( data.patientHasFeature(day, patient, feature) ) counts[feature]++ ;
          }
          this.total++ ;    
        }
      }
    }
  }

  private double likelihood(Data data, int day, int patient, Window calibrationWindow) {
    double likelihood = 1.0 ;
    for (int feature=0 ; feature<numberOfFeatures() ; feature++) {
      if ( data.patientHasFeature(day, patient, feature) && features.isLegal(feature) ) {
        likelihood *= calibrationWindow.theta(feature) ;
      }
      else likelihood *= (1.0 - calibrationWindow.theta(feature) ) ;
    }
    return likelihood ;
  }

}

