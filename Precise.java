/// Contents: Methods for log arithmetic.
/// Author:   John Aronis
/// Date:     March 2017

public class Precise {

  public static void main(String args[]) {
    for (int n=0 ; n<20 ; n++) {
      System.out.println( factorialLog(n) ) ;
    }
  }

  private boolean isZero ;
  private double logOfValue ;
  private static final double MAX_EXP = -50000.0;

  public Precise(double x) {
    if (x<=0.0) { isZero = true ; } else { logOfValue = Math.log10(x) ; isZero = false ; }
  }

  public boolean isZero() { return isZero ; }

  public boolean isLessThan(Precise p) {
    if ( this.isZero && p.isZero ) return false ;
    else if ( this.isZero && !p.isZero ) return true ;
    else if ( !this.isZero && p.isZero ) return false ;
    else return ( this.logOfValue < p.logOfValue ) ;
  }

  public boolean isGreaterThan(Precise p) { return p.isLessThan(this) ; }

  private static double logOfSum(double logX, double logY) {
    double logYMinusLogX, temp;
    if (logY>logX) { temp=logX; logX=logY; logY=temp; }
    logYMinusLogX=logY-logX;
    if (logYMinusLogX<MAX_EXP) { return logX; }
    else { return Math.log10(1+Math.pow(10,logYMinusLogX))+logX; }
  }

  public Precise add(Precise x) {
    if (this.isZero && x.isZero) return this ;
    if (x.isZero)                return this ;
    if (this.isZero)             return x ;
    Precise result = new Precise(0.0) ;
    result.isZero = false ;
    result.logOfValue = logOfSum(this.logOfValue,x.logOfValue) ;
    return result ;
  }

  public Precise multiply(Precise x) {
    if (this.isZero || x.isZero) return new Precise(0.0) ;
    Precise result = new Precise(0.0) ;
    result.isZero = false ;
    result.logOfValue = this.logOfValue + x.logOfValue ;
    return result ;
  }

  public Precise divide(Precise x) {
    if (this.isZero ) return new Precise(0.0) ;
    Precise result = new Precise(0.0) ;
    result.isZero = false ;
    result.logOfValue = this.logOfValue - x.logOfValue ;
    return result ;
  }

  public static Precise power(double base, double exponent) {
    if ( exponent == 0.0 ) return new Precise(1.0) ;
    Precise result = new Precise(base) ;
    result.logOfValue *= exponent ;
    return result ;
  }

  private static double factorialLog(int n) {
    switch (n) {
      case 0: return 0.0 ;
      case 1: return 0.0 ;
      case 2: return 0.3010299956639812 ;
      case 3: return 0.7781512503836436 ;
      case 4: return 1.380211241711606 ;
      case 5: return 2.0791812460476247 ;
      case 6: return 2.8573324964312685 ;
      case 7: return 3.7024305364455254 ;
      case 8: return 4.605520523437469 ;
      case 9: return 5.559763032876794 ;
    }
    return n*(Math.log10(n)-Math.log10(Math.E))+Math.log10(Math.sqrt(2.0*Math.PI*n)) ;
  }

  private static double combinationsLog(int n, int r) {
    return factorialLog(n)-(factorialLog(r)+factorialLog(n-r)) ;
  }

  public static Precise combinations(int n, int r) {
    Precise result = new Precise(0.0) ;
    if (r>n) { return result ; }
    result.isZero = false ;
    result.logOfValue = combinationsLog(n,r) ;
    return result ;
  }

  public static Precise binomial(int n, int k, double p) {
    return combinations(n,k).multiply(power(p,k)).multiply(power(1.0-p,(n-k))) ;
  }

  public double clear() {
    if (isZero) { return 0.0 ; } else { return Math.pow(10,this.logOfValue) ; }
  }

  public double log() { return this.logOfValue ; }

  public String toString() { return "" + clear() ; }

}

/// End-of-File
