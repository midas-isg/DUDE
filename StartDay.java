public class StartDay {

  static int    BASELINE  = 122 ;
  static int    WINDOW    = 10 ;
  static double THRESHOLD = 0.01 ;

  public static int startDay(Data data) {
    return startDay(data, BASELINE, WINDOW, THRESHOLD) ;
  }

  static double lambda(Data data, int baseline, int window) {
    int total = 0 ;
    for (int day=0 ; day<baseline-window ; day++) total += data.numberOfPatients(day,window) ;
    return (double)total/(double)(baseline-window) ;
  }

  static double poisson(Data data, int day, int window, double lambda) {
    int k = data.numberOfPatients(day,window) ;
    return ( Math.pow(lambda,k) * Math.pow(Math.E,-lambda) ) / factorial(k) ;
  }

  static int startDay(Data data, int baseline, int window, double threshold) {
    double lambda, p ;
    int count ;
    lambda = lambda(data,baseline,window) ;
    count = 0 ;
    for (int day=baseline ; day<data.numberOfDays()-window ; day++) {
      p = poisson(data,day,window,lambda) ;
      if ( p<=threshold ) return day ;
    }
    return -1 ;
  }

  static int factorial(int n) { if (n==0) return 1 ; else return n*factorial(n-1) ; }

}

/// End-of-File
