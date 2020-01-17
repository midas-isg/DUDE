Contents: DUDE (Detect Unmodeled Disease from Evidence) system to detect
  the presence of an unmodeled ILI from patient record data
Author:   John Aronis, Department of Biomedical Informatics,
  University of Pittsburgh

This directory contains Java source code for DUDE system to detect the
presence of a new ILI from patient findings data.

Compile with:

    javac RunDUD.java

Run with:

    java RunDUD <start day> <wait period> <monitor window> <dataset>

Where <start day> is the day to begin surveillance, <wait period> is
the number of days between the baseline window and monitor window, and
<monitor window> is the number of days in the monitor window.

The <dataset> is a space-separated file of patient records, with one
record per line, with the following fields:

    date other_pneumonia chest_wall_retractions toxic_appearance
    viral_syndrome paroxysmal_cough hoarseness viral_pneumonia
    poor_response_of_fever_to_antipyretics lab_positive_rhinovirus
    staccato_cough diarrhea pharyngitis_diagnosis stridor vomiting
    hypoxemia other_abnormal_breath_sounds ill_appearing headache
    lab_positive_strep_a lab_positive_enterovirus
    lab_testing_ordered_influenza AC000006 anorexia arthralgia dyspnea
    wheezing lab_testing_ordered_rsv abdominal_pain apnea
    bronchiolitis croup AC000002 conjunctivitis
    cervical_lymphadenopathy decreased_activity crackles
    respiratory_distress lab_testing_ordered_influenza_with_other
    nausea infiltrate rigor lab_positive_rsv lab_positive_influenza
    malaise lab_positive_adenovirus grunting nasal_flaring
    highest_measured_temperature uri acute_onset hemoptysis
    lab_positive_coronavirus rhonchi chest_pain influenza_like_illness
    seizure lab_positive_hmpv AC000005 cyanosis stuffy_nose rales
    myalgia tachypnea other_cough runny_nose reported_fever
    pharyngitis_on_exam abnormal_chest_radiograph_findings
    lab_order_nasal_swab productive_cough barking_cough
    lab_positive_parainfluenza streptococcal_pharyngitis
    weakness_or_fatigue abdominal_tenderness
    bilateral_acute_conjunctivitis chills sore_throat poor_feeding
    bronchitis nonproductive_cough age_under_six_years

The date field is MM-DD and other fields are T/F.

For each day starting with <start day> DUDE computes baseline
statistics from the records from day 1 through the day
<start day>-(<wait period>+<monitor window>) and outputs the
log-likelihood of the presence of an unmodeled disease in the
records from day <start day>-<monitor wndow> through <start day>.


