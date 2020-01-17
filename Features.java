public class Features {

  private boolean[] legal ;

  public Features() {
    legal = new boolean[features.length] ;
    for (int i=0 ; i<legal.length ; i++) { legal[i] = true ; }
    for (String feature : illegalFeatures) { makeIllegal(feature) ; }
  }

  public int numberOfFeatures() { return features.length ; }

  public String featureName(int featureNumber) { return features[featureNumber] ; }

  public int featureNumber(String featureName) {
    for (int f=0 ; f<features.length ; f++) { if ( features[f].equals(featureName) ) return f ; }
    return -1 ;
  }

  public void makeIllegal(String featureName) { legal[featureNumber(featureName)] = false ; }

  public boolean isLegal(int featureNumber) { return legal[featureNumber] ; }

  private String[] features = {
    "other_pneumonia", "chest_wall_retractions", "toxic_appearance",
    "viral_syndrome", "paroxysmal_cough", "hoarseness", "viral_pneumonia",
    "poor_response_of_fever_to_antipyretics", "lab_positive_rhinovirus",
    "staccato_cough", "diarrhea", "pharyngitis_diagnosis", "stridor",
    "vomiting", "hypoxemia", "other_abnormal_breath_sounds",
    "ill_appearing", "headache", "lab_positive_strep_a",
    "lab_positive_enterovirus", "lab_testing_ordered_influenza",
    "AC000006", "anorexia", "arthralgia", "dyspnea", "wheezing",
    "lab_testing_ordered_rsv", "abdominal_pain", "apnea", "bronchiolitis",
    "croup", "AC000002", "conjunctivitis", "cervical_lymphadenopathy",
    "decreased_activity", "crackles", "respiratory_distress",
    "lab_testing_ordered_influenza_with_other", "nausea", "infiltrate",
    "rigor", "lab_positive_rsv", "lab_positive_influenza", "malaise",
    "lab_positive_adenovirus", "grunting", "nasal_flaring",
    "highest_measured_temperature", "uri", "acute_onset", "hemoptysis",
    "lab_positive_coronavirus", "rhonchi", "chest_pain",
    "influenza_like_illness", "seizure", "lab_positive_hmpv", "AC000005",
    "cyanosis", "stuffy_nose", "rales", "myalgia", "tachypnea",
    "other_cough", "runny_nose", "reported_fever", "pharyngitis_on_exam",
    "abnormal_chest_radiograph_findings", "lab_order_nasal_swab",
    "productive_cough", "barking_cough", "lab_positive_parainfluenza",
    "streptococcal_pharyngitis", "weakness_or_fatigue",
    "abdominal_tenderness", "bilateral_acute_conjunctivitis", "chills",
    "sore_throat", "poor_feeding", "bronchitis", "nonproductive_cough",
    "age_under_six_years" } ;

  private static String[] illegalFeatures = {
    "highest_measured_temperature", "lab_positive_rhinovirus",
    "lab_positive_strep_a", "lab_positive_enterovirus",
    "lab_testing_ordered_influenza", "AC000006",
    "lab_testing_ordered_rsv", "AC000002",
    "lab_testing_ordered_influenza_with_other", "lab_positive_rsv",
    "lab_positive_influenza", "lab_positive_adenovirus",
    "lab_positive_coronavirus", "lab_positive_hmpv", "AC000005",
    "lab_order_nasal_swab", "lab_positive_parainfluenza"
  } ;

}
