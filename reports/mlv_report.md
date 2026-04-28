# Requirement Quality Report

> Note: This report is summarized — Ambiguity rows: 300/489, conflict candidates: 200/53512. Long texts may be truncated.

## Evaluation (Ambiguity)

- Threshold (cutoff that turns score into 0/1 label): 0.35
- Precision (fraction of predicted positives that are correct): 0.333
- Recall (fraction of gold positives that were found): 0.429
- F1 (harmonic mean of Precision and Recall): 0.375
- TP/FP/FN/TN: 3/6/4/7
- Evaluated rows: 20 (skipped: 469)

### Interpretation (short)

- If **Precision** is low: we raise many **false alarms (FP)**.
- If **Recall** is low: we **miss gold positives (FN)**.
- **F1** summarizes the balance between precision and recall; in this run F1=0.375 with TP/FP/FN/TN=3/6/4/7, so the score is driven by **FP and/or FN** at this threshold.

### Best threshold (threshold sweep)

- Best threshold (τ* that maximizes F1): 0.15
- Precision (fraction of predicted positives that are correct): 0.429
- Recall (fraction of gold positives that were found): 0.857
- F1 (harmonic mean of Precision and Recall): 0.571
- TP/FP/FN/TN: 6/8/1/5

### Top 5 thresholds (by F1)

| Threshold | Precision | Recall | F1 | TP/FP/FN/TN |
|---:|---:|---:|---:|---|
| 0.15 | 0.429 | 0.857 | 0.571 | 6/8/1/5 |
| 0.20 | 0.429 | 0.857 | 0.571 | 6/8/1/5 |
| 0.05 | 0.375 | 0.857 | 0.522 | 6/10/1/3 |
| 0.10 | 0.375 | 0.857 | 0.522 | 6/10/1/3 |
| 0.00 | 0.350 | 1.000 | 0.519 | 7/13/0/0 |

## Ambiguity findings

| ID | Score | Text | Reasons |
|---|---:|---|---|
| GUI.45.6 | 0.65 | − > GUI.46.2 iii Contents Definitions and Abbreviations vii 1 Introduction 1 1.1 Mechanical lung ventilator . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 1.2 Structure of the document . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 2 1.3 Suggested… | Optionality/weak modal verbs (e.g., may/should/could).; Open-ended temporal phrases (e.g., as needed/immediately).; Potentially ambiguous pronouns (it/this/they). |
| CONT.42 | 0.45 | ). 38 4.4 PSV Mode).In PCV and PSV modes there shall be the possibility to FUN.28 initiate an Expiratory Pause if it is set by the GUI. Rationale: The Expiratory Pause will initiate a forced hold at the end of expiration, allowing the measurement of the AutoPEEP level for the pa… | Optionality/weak modal verbs (e.g., may/should/could).; Potentially ambiguous pronouns (it/this/they). |
| FUN.28 | 0.45 | In PCV and PSV mode there shall be the possibility to ISO 80601-2- press a single button to initiate an Expiratory Pause 12 201.107.1 that closes both inspiratory and expiratory valve at the end of the expiration phase as long as the operator holds the button but no longer than … | Optionality/weak modal verbs (e.g., may/should/could).; Potentially ambiguous pronouns (it/this/they). |
| FUN.4 | 0.45 | and updated Fig. 2.1 Added new requirement about exit from Fail-safe modeThe system shall implement the following operating modes: 1. Start-up Mode: The Start-up Mode initializes the system and is part of a start-up procedure to get the system ready to be used to ventilate a pat… | Optionality/weak modal verbs (e.g., may/should/could).; Potentially ambiguous pronouns (it/this/they). |
| FUN.5 | 0.45 | The Start-up Mode shall be initiated by pushing the power button of the system once the system has been connected to the breathing circuit (without connection to the patient), the air supply, and the power source. Rationale: need to turn on the system once all connections are at… | Optionality/weak modal verbs (e.g., may/should/could).; Possible passive voice (may hide actor). |
| FUN.6 | 0.45 | The system shall have a self-test procedure that ensures ISO80601- the system and its accessories are fully functional and the 2-12 alarms work. 201.7.9.2.8.101) shall be mandatorycan be skipped or optionally rerun individually.and its sub-requirements), if they pass, then the v… | Optionality/weak modal verbs (e.g., may/should/could).; Potentially ambiguous pronouns (it/this/they). |
| GUI.13 | 0.45 | If Controller is ventilating: Rationale: if the GUI starts and the Controller is already ventilating the GUI shall be able to display the informa- tion of the ventilation. This can happen e.g. if the GUI crashes while running ventilation and then it is restarted. | Optionality/weak modal verbs (e.g., may/should/could).; Potentially ambiguous pronouns (it/this/they). |
| GUI.2 | 0.45 | The transition from Start-up Mode to Ventilation occurs FUN.10 if the GUI finds that the controller is already running in ventilation mode. In this case, when exiting the start-up mode, it resumes the ventilation Rationale: the GUI may have crashed while the controller keeps ven… | Optionality/weak modal verbs (e.g., may/should/could).; Potentially ambiguous pronouns (it/this/they). |
| GUI.57 | 0.45 | The user shall be able to enter the PIN to unlock the GUI. Rationale: a pin is necessary to unlock the GUI to be sure that the user wants to unlock it. Only holding a button is not enough because it can be done accidentally. | Optionality/weak modal verbs (e.g., may/should/could).; Potentially ambiguous pronouns (it/this/they). |
| AL.17 | 0.35 | Leakage in gas circuit SAV.14 The system shall raise an alarm if there is significant leak- age in the gas circuit. Alarm condition Unintended leakage from the ventila- tor should not exceed: 200 ml/min at 50 cmH20 Priority MEDIUM Raised by Controller 45 5 Alarms | Optionality/weak modal verbs (e.g., may/should/could). |
| AL.5 | 0.35 | Additional requirements for 1 m (operator’s position) vi- ISO 80601- sual alarm signals and information signals High priority 2-12 alarm signals should be accompanied by information de- 208.6.3.2.2.2.101 scribing possible causes of the alarm condition and appro- priate actions t… | Optionality/weak modal verbs (e.g., may/should/could). |
| CONT.41 | 0.35 | ).).In PCV and PSV modes there shall be the possibility to FUN.29 initiate an Inspiratory Pause if it is set by the GUI. Rationale: The Inspiratory Pause will initiate a forced hold at the end of inspiration, allowing for the measure- ment of the Plateau Pressure (PP), the press… | Optionality/weak modal verbs (e.g., may/should/could). |
| FUN.23 | 0.35 | The system shall provide means to switch from PCV to PSV while PCV ventilation is active. Rationale: the switchover between modes should not re- quire stopping the ventilation in order to maintain venti- lation of the patient | Optionality/weak modal verbs (e.g., may/should/could). |
| FUN.29 | 0.35 | In PCV and PSV mode there shall be the possibility to ISO 80601-2- press a single button to initiate an Inspiratory Pause 12 (IP) that closes both inspiratory and expiratory valve at 201.107.2 the end of the inspiratory phase as long as the operator holds the button but no longe… | Optionality/weak modal verbs (e.g., may/should/could). |
| GUI.108 | 0.35 | , GUI.109, GUI.110 1.4 November 6, 2023 Updated Fig. 3.1The transition from Ventilation to Settings shall occur if the operator wants to adjust ventilation settings. Settings Mode: During Settings mode, the operator can change the parameters of the venti- lation and of the alarm… | Optionality/weak modal verbs (e.g., may/should/could). |
| GUI.3 | 0.35 | The transition from Start-up Mode to Start shall occur FUN.10 once the configurations have been loaded and the con- troller is not running in ventilation. Start Mode: In start mode, the user can resume ventilation or start ventilation on a new patient. 25 3 GUI Requirements | Optionality/weak modal verbs (e.g., may/should/could). |
| GUI.61 | 0.35 | The user shall be able to start ventilation in PCV or PSV by pressing a start button. Rationale: the user can choose the ventilation mode. | Optionality/weak modal verbs (e.g., may/should/could). |
| GUI.62 | 0.35 | The user shall be able to stop ventilation by pressing a stop button if running. Rationale: if the user wants to stop the ventilation in PCV or PSV mode he can do it under his responsibility. | Optionality/weak modal verbs (e.g., may/should/could). |
| GUI.84 | 0.35 | The GUI shall display the current value of the parameters Rationale: the user can see current value of ventilator pa- rameters | Optionality/weak modal verbs (e.g., may/should/could). |
| PER.4 | 0.35 | - PER.7 and can be changed/controlled by the user in the allowed requirements indicated- PER.7Respiratory Rate (RRPCV ) Default Range Step Size 12 b/min 4-50 b/min 1 b/min | Optionality/weak modal verbs (e.g., may/should/could). |
| PER.8 | 0.35 | - PER.14 and can be changed/controlled by the user in the allowed range- PER.14Target inspiratory pressure (Pinsp PSV ) Default Range Step Size 15 cm H2O 2-50 cm H2O 1 cm H2O | Optionality/weak modal verbs (e.g., may/should/could). |
| SAV.1 | 0.35 | The system shall raise an alarm of at least low priority ISO 80601- when the delivered oxygen concentration changes by more 2-12 than 3% volume of a user-controlled target FiO2 value. 201.12.4.101 Rationale: a drop of oxygen concentration might indicate a failing gas supply. | Optionality/weak modal verbs (e.g., may/should/could). |
| SAV.14 | 0.35 | The system shall raise an alarm if there is significant leak- age in the gas circuit (200 ml/min at 50 cmH2O) Rationale: ISO 80601-2-12 201.102.7.1 (a) requires that there should not be unintended leakage in the patient breathing circuit of higher than (200 ml/min at 50 cmH2O). … | Optionality/weak modal verbs (e.g., may/should/could). |
| SAV.3 | 0.35 | - SAV.9 Rationale: alarm thresholds may vary from patient to pa- tientThe system shall raise an alarm (at least low prior- ity) when the minimum inspiratory airway pressure (Min Pinsp ) is not achieved. | Optionality/weak modal verbs (e.g., may/should/could). |
| CONT.43 | 0.30 | ).).In PCV and PSV mode, at the end of an inspiration FUN.22 and if inspiratory pause is not required, it shall be possi- ble to initiate a lung recruitment procedure, termed Re- cruitment Maneuver (RM), if it is required by the GUI. Not available in North America. Rationale: Th… | Open-ended temporal phrases (e.g., as needed/immediately).; Potentially ambiguous pronouns (it/this/they). |
| FUN.22 | 0.30 | In PCV mode it shall be possible to initiate with the push of a single button a lung recruitment procedure, termed Recruitment Maneuver (RM). Note: This maneuver is not allowed in North America. Rationale: The RM is an emergency procedure required immediately after intubation. R… | Open-ended temporal phrases (e.g., as needed/immediately).; Potentially ambiguous pronouns (it/this/they). |
| CONT.38 | 0.25 | When the ventilator is in Start-up or VentilationOff mode, the in valve pressure shall be set to close and the out valve shall be open. Rationale: if the machine is not ventilating the valves are in a secure configuration state, in valve is closed and out valve is opened. | Vague quality adjectives (e.g., fast/intuitive/robust). |
| AL.12 | 0.20 | (Acknowledgment) The machine shall allow the oper- IEC 60601-1-8 ator to cease the alarm signal for which no associated 6.8 alarm condition currently exists (ALARM RESET). 43 5 Alarms 5.1 Alarm list All the alarms shall be displayed on the GUI (except those referred to GUI failu… | Possible passive voice (may hide actor).; Potentially ambiguous pronouns (it/this/they). |
| CONT.20 | 0.20 | In PCV mode the breathing cycle shall be defined by in- FUN.19 spiratory pressure Pinsp PCV relative to atmosphere, res- piratory rate (RRPCV ) and the ratio between the inspi- ratory and expiratory times (I:EPCV ). Rationale: this is the most appropriate procedure for COVID-19 … | Possible passive voice (may hide actor).; Potentially ambiguous pronouns (it/this/they). |
| CONT.36.3 | 0.20 | min exp time psv shall be the half of the last inspiration time. min exp time psv shall be in the interval [0.4 : 2] sec. Rationale: The min exp time psv prevents moving immediately to inspiration allowing the patient to expirate. | Open-ended temporal phrases (e.g., as needed/immediately). |
| CONT.41.2 | 0.20 | When inspiratory pause timeout (max ins pause) is over, the cycle shall proceed immediately to expiration. Rationale: The timeout prevents stopping the patient breath cycle in case of human error. | Open-ended temporal phrases (e.g., as needed/immediately). |
| CONT.42.2 | 0.20 | When expiratory pause timeout (max exp pause) is over, the cycle shall proceed immediately to inspiration. Rationale: the timeout prevents stopping the patient breath cycle in case of human error. | Open-ended temporal phrases (e.g., as needed/immediately). |
| CONT.44 | 0.20 | If PAW exceeds Max Pinsp during inspiration, the cycle FUN.40 shall proceed immediately to expiration. | Open-ended temporal phrases (e.g., as needed/immediately). |
| FUN.5.2 | 0.20 | The system shall indicate to the user that the initializa- tion process has been completed successfully or failed. In case of a failure the user shall be warned that the system is out-of-service. In addition, any other operations shall be not allowed. Rationale: only a fully fun… | Possible passive voice (may hide actor).; Potentially ambiguous pronouns (it/this/they). |
| GUI.13.2 | 0.20 | The GUI shall be able to immediately move to the main ventilation screen (Ventilation Mode) after having loaded parameters from the Controller. | Open-ended temporal phrases (e.g., as needed/immediately). |
| GUI.49.1 | 0.20 | − > GUI.49The GUI shall be able to start the ventilation as needed FUN.10 3.4 Self-Test Mode | Open-ended temporal phrases (e.g., as needed/immediately). |
| SAV.25 | 0.20 | The ventilator shall be equipped with an alarm system ISO 80601- that detects a technical alarm condition to indicate when 2-12 conditions in the patient breathing circuit reach the alarm 201.12.4.109 limit (Min PEEP) for disconnection. (related to essential performance as per T… | Possible passive voice (may hide actor).; Potentially ambiguous pronouns (it/this/they). |
| SAV.4 | 0.20 | ) (e.g. by coughing) by truncating the inspiratory phase and immediately transitioning to expiration, quickly re- lieving the pressure. Rationale: need to avoid excessive pressure in the lungsThe system shall raise a high priority alarm when the ISO 80601- peak inspiratory airwa… | Open-ended temporal phrases (e.g., as needed/immediately). |
| AL.10 | 0.10 | If an operator-adjustable alarm limit is provided the IEC 60601-1-8 alarm limit shall be displayed when required by the op- 6.6.2.1 erator | Possible passive voice (may hide actor). |
| AL.14.1 | 0.10 | Battery fail SAV.2 A battery failure when the system is powered from AC shall be detected and signalled. Alarm condition Battery failure Priority HIGH Raised by Controller | Possible passive voice (may hide actor). |
| AL.21 | 0.10 | Obstruction in exhale branch - PEEP too high SAV.24 Obstruction in the exhale branch shall be raised if the PEEP is out of range. Alarm condition PEEP > Max PEEP Priority HIGH Raised by Controller | Possible passive voice (may hide actor). |
| AL.8 | 0.10 | An alarm system shall be provided with at least one man- IEC 60601-1-8 ufacturer configured alarm preset. 6.5.2 | Possible passive voice (may hide actor). |
| CONT.11 | 0.10 | Final State shall be reached by pushing the power button located on the back side of the ventilator unit to turn it off. Rationale: the ventilator is turned off when the user pushes the power button on the ventilator unit. | Possible passive voice (may hide actor). |
| CONT.12 | 0.10 | A set of default values for all parameters shall be provided FUN.5 and loaded from a configuration file on the machine when it is turned on. The parameters are listed in Section 3.1.1. | Possible passive voice (may hide actor). |
| CONT.2 | 0.10 | The transition to Start-up Mode shall be allowed by push- FUN.5 ing the power button located on the back side of the ven- tilator unit to turn it on. 35 4 Controller Requirements | Possible passive voice (may hide actor). |
| CONT.22 | 0.10 | The cycle starts with the inspiration phase that lasts FUN.20 an Inspiratory time I = 60 x I:EPCV /(RRPCV x (1 + I:EPCV )) seconds. After that the expiration phase be- gins. | Potentially ambiguous pronouns (it/this/they). |
| CONT.26 | 0.10 | : when it detects a sud- den drop in pressure above the inhale trigger sensitivity (i.e., it yields the condition drop(PAW) > ITSPCV )) 1.5 December 13, 2023 Updated Fig. 3.1Within the trigger window during the expiratory phase, FUN.21 in the case of spontaneous breathing, the v… | Potentially ambiguous pronouns (it/this/they). |
| CONT.28 | 0.10 | The target inspiratory pressure level shall be controlled FUN.20 by the Inspiratory Pressure parameter (Pinsp PCV ) and it is kept constant. 4.4 PSV Mode ID Requirement / Rationale Input Ref. | Possible passive voice (may hide actor). |
| CONT.31 | 0.10 | The target inspiratory pressure level shall be controlled FUN.24 by the Inspiratory Pressure parameter (Pinsp PSV ). | Possible passive voice (may hide actor). |
| CONT.36.1 | 0.10 | A new inspiration shall be initiated by a sudden drop FUN.25 in pressure above the inhale trigger sensitivity (ITSPSV ), which shall be set by the user (i.e., it yields the condition drop(PAW) > ITSPSV )). Rationale: In a pressure-regulated ventilator, the speed of pressure drop… | Possible passive voice (may hide actor). |
| CONT.39 | 0.10 | When the ventilator is in an Inspiration state, the out valve shall be closed and the in valve pressure shall be set to target inspiratory pressure (Pinsp of the corresponding mode). | Possible passive voice (may hide actor). |
| CONT.40 | 0.10 | When the ventilator is in an expiration state the in valve shall be closed (pressure 0) and the out valve shall be open. Rationale: The ventilator opens the out valve to allow the patient to expirate, while the in valve is closed to avoid air in. 40 4.5 Requirements Common to al… | Possible passive voice (may hide actor). |
| CONT.43.2 | 0.10 | The Recruitment Maneuver, if not actively stopped by the GUI, has a timeout (max rm time). After the Re- cruitment Maneuver phase expiration phase begins. Rationale: It is not reasonable to keep the patient in this emergency state without letting him breathe. | Potentially ambiguous pronouns (it/this/they). |
| CONT.43.3 | 0.10 | In RM the out valve shall be closed and the in valve shall FUN.22 be opened to allow lung inflation at PRM. | Possible passive voice (may hide actor). |
| FUN.18.2 | 0.10 | When enabled, the leak compensation shall be activated | Possible passive voice (may hide actor). |
| FUN.19 | 0.10 | The system shall have a pressure control venti- lation (PCV) mode, as characterized by the fol- lowing plots of pressure and flow versus time. Rationale: this is the most appropriate procedure for COVID-19 patients as it allows the immediate reopen- ing of the alveoli and is str… | Potentially ambiguous pronouns (it/this/they). |
| FUN.20 | 0.10 | Removed “patient connected” from Fig. 2.1 1.3 October 25, 2023 Updated Fig. 2.1 3.1 4.1In PCV mode, the breathing cycle shall be defined by in- spiratory pressure Pinsp PCV relative to atmosphere, res- piratory rate (RRPCV ) and the ratio between the inspi- ratory and expiratory… | Possible passive voice (may hide actor). |
| FUN.21 | 0.10 | In PCV mode, a new breathing cycle shall be initiated either after a breathing cycle is over, or by patient request during expiration. Rationale: while the main mode of PCV is the control of the breathing cycle timing by the ventilator, the patient has to have the ability to tri… | Possible passive voice (may hide actor). |
| FUN.21.1 | 0.10 | A new breathing cycle shall be initiated by a sudden drop in pressure above a user-settable threshold (Inhale Trigger Sensitivity). Rationale: In a pressure-regulated ventilator, the intensity of pressure drop initiated by the patient is the easiest way to detect the spontaneous… | Possible passive voice (may hide actor). |
| FUN.25 | 0.10 | In PSV mode the breathing cycle shall be initiated by a sudden drop in pressure above a user-settable threshold (Inhale Trigger Sensitivity) Rationale: In a pressure-regulated ventilator, the speed of pressure drop initiated by the patient is the easiest way to detect the sponta… | Possible passive voice (may hide actor). |
| FUN.27.1 | 0.10 | If apnea is detected, an apnea alarm shall be triggered. Rationale: Clinician needs to be made aware of the fact that patient stops breathing | Possible passive voice (may hide actor). |
| FUN.5.1 | 0.10 | Upon initiation of the Start-up Mode, the system shall go through an initialization process that loads default pa- rameters and checks the system memory and the commu- nication of the controller with the sensors and valves, as well as between the controller and the GUI. Rational… | Potentially ambiguous pronouns (it/this/they). |
| FUN.7 | 0.10 | If the self-test fails, the user shall be warned that the system is out-of-service. In addition, any other operations shall be not allowed. | Possible passive voice (may hide actor). |
| FUN.8.2 | 0.10 | The system shall be equipped with an alarm system log ISO 80601- with a capacity of at least 1000 events in total for: high 2-12: priority alarm conditions; medium priority alarm condi- 208.6.12.101 tions; and alarm signal inactivation states | Possible passive voice (may hide actor). |
| GUI.103 | 0.10 | Frozen waveforms shall be shifted and re-scaled along both the vertical and the horizontal axes. | Possible passive voice (may hide actor). |
| GUI.15 | 0.10 | The patient shall not be connected to the breathing cir- FUN.5.3 cuit when the system is powered on and through start-up and self-test, a warning message shall be displayed at sys- tem startup. 3.1.1 Start-up Configuration Parameters A set of default values for all parameters sh… | Possible passive voice (may hide actor). |
| GUI.45.2 | 0.10 | When resuming ventilation, the GUI shall be able to FUN.10 load setting parameters from the last known configura- tion saved by the system and stored in the system. This configuration is protected by a md5 file to guarantee that the settings are not corrupted. Before loading, th… | Potentially ambiguous pronouns (it/this/they). |
| GUI.51 | 0.10 | When the GUI enters the Ventilation mode, it shall be able to show the real time data coming from the controller Rationale: this is the main screen containing all the data coming from the controller 29 3 GUI Requirements | Potentially ambiguous pronouns (it/this/they). |
| GUI.54 | 0.10 | The user shall be able to snooze an alarms when they have been raised. Rationale: the user is able to snooze alarms when they are raised by the system | Potentially ambiguous pronouns (it/this/they). |
| GUI.87.1 | 0.10 | Before sending the settings to the Controller, the GUI SAV.50 shall ask for confirmation to the user. After the con- firmation the GUI shall transmit the parameters to the Controller and check that the controller accepts the val- ues. The GUI shall read back the parameters from … | Potentially ambiguous pronouns (it/this/they). |
| PER.14 | 0.10 | Target inspiratory pressure (Pinsp AP ) Default Range Step Size 0 cm H2O 2-50 cm H2O 1 cm H2O Rationale: Default value of Pinsp AP in the apnea backup is left unset to ensure that the user set it in PSV mode in case of apnea lag. 17 2 System Requirements 2.3.4 Default Alarm Thre… | Potentially ambiguous pronouns (it/this/they). |
| PER.3 | 0.10 | The user shall be able to start Recruitment Maneuver (RM). Note: Function shall be disabled in North Amer- ica set by the GUI, a Recruitment Maneuver shall start (see | Possible passive voice (may hide actor). |
| SAV.12 | 0.10 | The system shall raise a high priority alarm when the ISO 80601- airway pressure measured near the input valve is below a 2-12 user-controlled value (Min Pinsp). 201.12.4.106 (b1) Obstruction in the inhale branch shall be raised if the PAW is out of range. Alarm condition PAW < … | Possible passive voice (may hide actor). |
| SAV.2.2 | 0.10 | When the backup battery nears depletion, and at least 10 ISO 80601- min remain until the loss of ventilation at least a medium 2-12 priority alarm shall be raised. 201.11.8.101 (f1) | Possible passive voice (may hide actor). |
| AL.1 | 0.00 | The user shall be able to set alarm thresholds when the FUN.39 ventilator is either ventilating or not. | No common ambiguity cues detected. |
| AL.1.1 | 0.00 | If ventilator is ventilating, the alarm threshold used while FUN.39 user changes are the last saved. | No common ambiguity cues detected. |
| AL.11 | 0.00 | During adjustment of any alarm limit, the alarm system IEC 60601-1-8 shall continue to operate normally 6.6.2.3 | No common ambiguity cues detected. |
| AL.13 | 0.00 | Power disconnection SAV.2.1 The system has successfully switched over to backup bat- tery. Alarm condition PowerType(t1) = power and Power- Type(t2) = battery where t1<t2 Priority MEDIUM Raised by Controller | No common ambiguity cues detected. |
| AL.14 | 0.00 | Battery Failure SAV.2 | No common ambiguity cues detected. |
| AL.14.2 | 0.00 | When the ventilator is connected to the battery and 10 SAV.2.2 minutes of backup battery power remain. Alarm condition Remaining battery time < 10 min Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.14.3 | 0.00 | When the ventilator is connected to the battery and 5 SAV.2.3 minutes of backup battery power remain. Alarm condition Remaining battery time < 5 min Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.15 | 0.00 | Internal power voltages SAV.2 | No common ambiguity cues detected. |
| AL.15.1 | 0.00 | System Internal power under-over voltage SAV.2 The system shall raise an alarm if the internal power volt- ages are out of safe ranges. Alarm condition SystemVoltage < 10.5 V or System- Voltage > 14.1 V Priority HIGH Raised by Controller 44 5.1 Alarm list | No common ambiguity cues detected. |
| AL.15.2 | 0.00 | Power supply under-over voltage SAV.2 The system shall raise an alarm if the power supply volt- ages are out of safe ranges. Alarm condition PowerSupply < 11 V or PowerSupply > 14.5 V Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.15.3 | 0.00 | Battery supply under-over voltage SAV.2 The system shall raise an alarm if the battery supply volt- ages are out of safe ranges. Alarm condition BatteryVoltage < 4.5 V or BatteryVoltage > 5.2 V Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.15.4 | 0.00 | GUI under-over voltage SAV.2 The system shall raise an alarm if the GUI power voltages are out of safe ranges. Alarm condition GUIVoltage < 4.7 V GUIVoltage > 5.5 V Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.16 | 0.00 | ADC failure FUN.33 The system shall raise an alarm if ADC devices do not respond after a fixed number of retries (maximum 5) or report an error. Alarm condition ADC is not responding Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.16.1 | 0.00 | Whenever the ADC alarm condition occurs, the controller shall drive input and output valves to their safe state, within no more than one respiratory cycle. | No common ambiguity cues detected. |
| AL.18 | 0.00 | Complete Obstruction in pneumatic circuit SAV.15 The system shall raise an obstruction in pneumatic circuit alarm when the alarm limit for obstruction is reached. Alarm condition Internal pressure exceeds 5bar and PAW is below 1.5bar in two consecu- tive observations Priority HI… | No common ambiguity cues detected. |
| AL.18.1 | 0.00 | The controller shall drive input and output valves to their safe state. | No common ambiguity cues detected. |
| AL.19 | 0.00 | Partial Obstruction in pneumatic circuit SAV.15 The system shall raise an partial obstruction in pneumatic circuit alarm when alarm limit for partial obstruction is reached. Alarm condition Internal pressure exceeds double of PAW in two consecutive observations Priority HIGH Rai… | No common ambiguity cues detected. |
| AL.2 | 0.00 | The visual alarms shall follow the requirements listed in Section 5.2. | No common ambiguity cues detected. |
| AL.20 | 0.00 | Obstruction in inhale branch - Inspiratory airway pres- SAV.3 | No common ambiguity cues detected. |
| AL.20.1 | 0.00 | The alarm condition delay shall not exceed more than two respiratory cycles or 5 s, whichever is greater. | No common ambiguity cues detected. |
| AL.21.1 | 0.00 | The alarm condition delay shall not exceed more than two SAV.24.1 respiratory cycles or 5 s, whichever is greater. | No common ambiguity cues detected. |
| AL.21.2 | 0.00 | Whenever the obstruction alarm condition occurs, the controller shall drive input and output valves to their safe state, within no more than one respiratory cycle. 46 5.1 Alarm list | No common ambiguity cues detected. |
| AL.22 | 0.00 | Obstruction in patient branch - Inspiratory flux too low SAV.15 | No common ambiguity cues detected. |
| AL.23 | 0.00 | Oxygen level too high SAV.1 The system shall raise an alarm when the delivered oxy- gen concentration value (%) exceeds the set FiO2 + 3%, i.e., due to failing gas supply. Alarm condition OS > desired FiO2+ 3% Priority MEDIUM Raised by Controller | No common ambiguity cues detected. |
| AL.24 | 0.00 | Oxygen level too low SAV.1 The system shall raise an alarm when the delivered oxy- gen concentration value (%) is below the set FiO2 - 3%, i.e., due to failing gas supply. Alarm condition OS < desired FiO2- 3% Priority MEDIUM Raised by Controller | No common ambiguity cues detected. |
| AL.25 | 0.00 | Inspiratory flux too high SAV.17 | No common ambiguity cues detected. |
| AL.26 | 0.00 | Inspiratory airway pressure too high SAV.4 | No common ambiguity cues detected. |
| AL.26.1 | 0.00 | The high airway pressure alarm condition delay shall not SAV.4.1 exceed 200 ms. | No common ambiguity cues detected. |
| AL.26.2 | 0.00 | Whenever the high-pressure alarm condition occurs, the SAV.4.2 controller shall raise the alarm, within no more than two respiratory cycles or 15 s. | No common ambiguity cues detected. |
| AL.26.3 | 0.00 | The controller shall drive input and output valves to their SAV.4.2 safe state reducing the airway pressure to either: the at- mospheric pressure; or the set PEEP level. 47 5 Alarms | No common ambiguity cues detected. |
| AL.27 | 0.00 | Disconnection alarm condition - PEEP too low SAV.5 | No common ambiguity cues detected. |
| AL.28 | 0.00 | Gas pressure input too low SAV.10 The system shall raise an alarm when the pressure at the entrance of the circuit is too low. Alarm condition When the input valve is open and the pressure out of the gas blender is too low: Pin GB < MIN Pin GB, MIN Pin GB =3800 cmH2O Priority ME… | No common ambiguity cues detected. |
| AL.29 | 0.00 | Gas pressure input too high SAV.11 The system shall raise an alarm when the pressure at the entrance of the circuit is too high. Alarm condition When the input valve is open and the pressure out of the gas blender is too high: Pin GB > MAX Pin GB, MAX Pin GB=5300 cmH2O Priority … | No common ambiguity cues detected. |
| AL.3 | 0.00 | The system shall have clearly ranked (high/medium/low ISO 60601-1-8 | No common ambiguity cues detected. |
| AL.30 | 0.00 | Over Temperature alarm SAV.21 The system shall raise an alarm if the internal temper- ature of the system exceeds 75◦C. The controller shall transition to the fail-safe mode. Alarm condition BoardTemperature > 75◦C Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.31 | 0.00 | Expiratory Vtidal exp too low SAV.8 The system shall raise an alarm when the patient is hypo-ventilating, i.e., expiratory tidal volume is below the Vtidal exp min limit. Alarm condition Vtidal exp < Min Vtidal exp Priority MEDIUM Raised by Controller 48 5.1 Alarm list | No common ambiguity cues detected. |
| AL.32 | 0.00 | Expiratory Vtidal exp too high SAV.9 The system shall raise an alarm when the patient is hyper- ventilating, i.e. expiratory tidal volume exceeds Vtidal exp max limit. Alarm condition Vtidal exp > Max Vtidal exp Priority MEDIUM Raised by Controller | No common ambiguity cues detected. |
| AL.33 | 0.00 | Inspiratory Vtidal insp too low SAV.8 The system shall raise an alarm when the patient inspi- ratory tidal volume is below the Vtidal insp min limit. Alarm condition Vtidal insp < Min Vtidal insp Priority MEDIUM Raised by Controller | No common ambiguity cues detected. |
| AL.34 | 0.00 | Inspiratory Vtidal insp too high SAV.9 The system shall raise an alarm when the inspiratory tidal volume exceeds Vtidal insp max limit. Alarm condition Vtidal insp > Max Vtidal insp Priority MEDIUM Raised by Controller | No common ambiguity cues detected. |
| AL.35 | 0.00 | Respiratory rate too low SAV.6 The system shall raise an alarm when the measured res- piratory rate is below Min RR. Alarm condition RR < Min RR Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.36 | 0.00 | Respiratory rate too high SAV.7 The system shall raise an alarm when the measured res- piratory rate exceeds Max RR. Alarm condition RR > Max RR Priority HIGH Raised by Controller 49 5 Alarms | No common ambiguity cues detected. |
| AL.37 | 0.00 | Apnea alarm SAV.22 The system shall raise an alarm when the time since last inspiration greater than apnea lag. Alarm condition When the expiratory duration in PSV mode is greater than the apnea lag. Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.38 | 0.00 | GUI failure 201.13.2.104 | No common ambiguity cues detected. |
| AL.38.1 | 0.00 | Controller failure 201.13.2.104 | No common ambiguity cues detected. |
| AL.39 | 0.00 | Unable to read sensor pressure 201.13.2.104 | No common ambiguity cues detected. |
| AL.4 | 0.00 | ALARM SYSTEMS Shall generate visual ALARM SIG- IEC 60601-1-8 NALS to indicate the presence of ALARM CONDI- 6.3.2.1 TIONS, their priority and each specific ALARM CON- DITION. | No common ambiguity cues detected. |
| AL.40 | 0.00 | Unable to read oxygen sensor 201.13.2.104 | No common ambiguity cues detected. |
| AL.41 | 0.00 | Unable to read sensor flux 201.13.2.104 | No common ambiguity cues detected. |
| AL.42 | 0.00 | Fan tachometer FUN.32 The controller shall raise an alarm if fan tachometer input indicates fan is not rotating. Alarm condition Fan is not rotating Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.42.1 | 0.00 | Whenever the FAN alarm condition occurs, the controller shall drive input and output valves to their safe state, within no more than one respiratory cycle. | No common ambiguity cues detected. |
| AL.43 | 0.00 | I:E ratio FUN.32 The I:E ratio is less than 0.01 for more than 4 consecutive cycles. Alarm condition I:E < 0.01 Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.43.1 | 0.00 | Whenever the I:E ratio alarm condition occurs, the con- troller shall drive input and output valves to their safe state, within no more than one respiratory cycle. | No common ambiguity cues detected. |
| AL.44 | 0.00 | Input valve failure FUN.32 The controller shall raise an alarm if the input valve (IN valve) does not change value. Alarm condition Input valve does not change value when the phase swaps from inspira- tory to expiratory and vice versa Priority HIGH Raised by Controller 51 5 Alar… | No common ambiguity cues detected. |
| AL.44.1 | 0.00 | Whenever the input valve alarm condition occurs, the controller shall drive input and output valves to their safe state, within no more than one respiratory cycle. | No common ambiguity cues detected. |
| AL.45 | 0.00 | Out valve failure FUN.51 The controller shall raise an alarm if out valve (OUT valve) does not change its state. Alarm condition Out valve does not change state when the phase swaps from expiratory to inspiratory and vice versa. Priority HIGH Raised by Controller | No common ambiguity cues detected. |
| AL.45.1 | 0.00 | Whenever the output valve alarm condition occurs, the controller shall drive input and output valves to their safe state, within no more than one respiratory cycle. | No common ambiguity cues detected. |
| AL.46 | 0.00 | SD GUI failure The system shall raise an alarm in case the GUI is not able to update the log file. Alarm condition The GUI is not able to update the log file Priority MEDIUM Raised by GUI 5.2 Visual alarm signals The IEC 60601-1-8 indicates the following characteristics of visua… | No common ambiguity cues detected. |
| AL.6 | 0.00 | If MULTIPLE ALARM CONDITIONS occur at the same IEC 60601-1-8 time, each individual ALARM CONDITION shall be vi- 6.3.2.2.2 sually indicated | No common ambiguity cues detected. |
| AL.7 | 0.00 | Visual information signals, if provided, shall be cor- IEC 60601-1-8 rectly perceived as different from HIGH PRIORITY or 6.3.2.2.2 MEDIUM PRIORITY visual alarm signals. Rationale: the user must be able to identify the priority of the alarms by using different colors for instance. | No common ambiguity cues detected. |
| AL.9 | 0.00 | The system shall prevent the operator from saving IEC 60601-1-8 changes to the alarm preset 6.5.2 | No common ambiguity cues detected. |
| CONT.1 | 0.00 | The controller shall implement the following modes (see FUN.4 Figure 4.1): | No common ambiguity cues detected. |
| CONT.1.1 | 0.00 | Start-up Mode: In start-up mode the controller initial- FUN.5 izes itself with default configuration parameters (if any), checks the system memory and the communication of the controller with the sensors and valves, as well as between the controller and the GUI. Start-up mode is… | No common ambiguity cues detected. |
| CONT.1.2 | 0.00 | Self-Test Mode: in the Self Test mode the controller al- FUN.6 lows the GUI to do all the operations necessary to perform the self-test. | No common ambiguity cues detected. |
| CONT.1.3 | 0.00 | VentilationOff: In ventilation off, the machine does not ventilate, the in valve is closed and the out valve is opened. | No common ambiguity cues detected. |
| CONT.1.4 | 0.00 | Pressure Controlled Ventilation Mode: Pressure Con- FUN.19 trolled Ventilation mode is used when patients have no spontaneous respiration. | No common ambiguity cues detected. |
| CONT.1.5 | 0.00 | Pressure Support Ventilation Mode: Pressure Support FUN.24 Ventilation mode is used when the patients are able to initiate every breath and the machine supports them. | No common ambiguity cues detected. |
| CONT.1.6 | 0.00 | Fail-safe: the controller forces input and output valves to their de-energized states (in valve close and out valve open) Start-up Mode: In start-up mode the controller initializes itself with default configuration parameters. | No common ambiguity cues detected. |
| CONT.10 | 0.00 | The transition from PSV to VentilationOff shall occur if the user stops the ventilation in PSV mode. Rationale: the ventilation stops when the user selects the stop command from the GUI. Fail-safe Mode: In fail-safe mode, the controller sets the valves to protect the patient. | No common ambiguity cues detected. |
| CONT.11.1 | 0.00 | During Final state, all parameters (if any) are to be safely stored before the final state is complete and the unit is de-energized. 4.1 Start-up Mode ID Requirement / Rationale Input Ref. | No common ambiguity cues detected. |
| CONT.13 | 0.00 | The controller shall check the communication of the con- FUN.5.1 troller with the sensors and valves. | No common ambiguity cues detected. |
| CONT.14 | 0.00 | The controller shall check the communication of the con- FUN.5.1 troller with GUI. | No common ambiguity cues detected. |
| CONT.15 | 0.00 | If the pressure sensor fails to connect or reports an error condition after a fixed number of retries (maximum 5), the controller shall transition to the fail-safe mode. | No common ambiguity cues detected. |
| CONT.16 | 0.00 | If the external ADC fails to initialize or reports an error condition after a fixed number of retries (maximum 5), the controller shall transition to the fail-safe mode. 4.2 SelfTest Mode ID Requirement / Rationale Input Ref. | No common ambiguity cues detected. |
| CONT.17 | 0.00 | During the self test mode the controller shall allow the FUN.6 | No common ambiguity cues detected. |
| CONT.18 | 0.00 | During the self test mode the controller shall perform the FUN.6 | No common ambiguity cues detected. |
| CONT.19 | 0.00 | If the SelfTest fails, the controller shall not be able to FUN.6 proceed to ventilation. 4.3 PCV Mode ID Requirement / Rationale Input Ref. 37 4 Controller Requirements | No common ambiguity cues detected. |
| CONT.21 | 0.00 | The breath cycle shall start with the inspiration phase. FUN.19 | No common ambiguity cues detected. |
| CONT.23 | 0.00 | At the end of an inspiration phase, if the Inspiratory FUN.29 Pause is set by the GUI, an Inspiratory Pause shall start | No common ambiguity cues detected. |
| CONT.24 | 0.00 | At the end of an inspiration phase, if inspiratory pause FUN.22 + | No common ambiguity cues detected. |
| CONT.25 | 0.00 | When in the expiration phase, a new inspiration shall be FUN.21 initiated either after a breathing cycle is over, or when a spontaneous breath is detected. The maximum duration of the expiration phase (i.e., the Expiratory time) yields E = 60 / (RRPCV x (1 + I:EPCV )) Rationale:… | No common ambiguity cues detected. |
| CONT.27 | 0.00 | If the controller is in the expiration phase, and it does FUN.28 not detect a spontaneous breath (i.e., the condition drop(PAW) > ITSPCV is false), within the expiration time, if the Expiratory Pause start is set by the GUI, | No common ambiguity cues detected. |
| CONT.29 | 0.00 | The Pressure Support Ventilation (PSV) mode shall sup- FUN.24 port the breathing of the patient with positive pressure up to a peak value of Pinsp PSV while the patient triggers ev- ery breath and maintains control of the respiratory rate. Rationale: PSV is not suitable for pati… | No common ambiguity cues detected. |
| CONT.3 | 0.00 | The transition from Start-up Mode to Self test Mode shall FUN.5 occur once the configurations have been loaded and the internal checking is terminated. Rationale: At this point, the monitoring module is able to carry out the assigned functionality. SelfTest Mode: In the Self Tes… | No common ambiguity cues detected. |
| CONT.30 | 0.00 | The breath cycle shall start with the inspiration phase. FUN.24 | No common ambiguity cues detected. |
| CONT.32 | 0.00 | The inspiration phase lasts until the inspiration peak is FUN.40 reached but no later than the max insp time psv is over. After that the expiration phase begins. Rationale: In PSV mode, the ventilator supports the pa- tient who is supposed to breathe spontaneously. In case a spo… | No common ambiguity cues detected. |
| CONT.33 | 0.00 | ) 3.2 Start Mode ID Requirement / Rationale Input Ref.When the inspiratory flow (VE) drops below a fraction FUN.26 of the peak flow (Expiratory Trigger Setting (ETS)) of a given breath (i.e., it yields the condition VE<ETS*Peak VE), the ventilator shall stop providing pressure a… | No common ambiguity cues detected. |
| CONT.34 | 0.00 | At the end of an inspiration phase, if the Inspiratory FUN.29 Pause is set by the GUI, an Inspiratory Pause shall start | No common ambiguity cues detected. |
| CONT.35 | 0.00 | At the end of an inspiration phase if no inspiration pause PER.3 is required and the Recruitment Maneuver (RM) is set by the GUI, a Recruitment Maneuver shall start (see | No common ambiguity cues detected. |
| CONT.36 | 0.00 | If the patient is in expiration phase: | No common ambiguity cues detected. |
| CONT.36.2 | 0.00 | If the controller is in expiration phase and a spontaneous FUN.28 breath is not detected (i.e., the condition drop(PAW) > ITSPSV ) is false), within the interval [min exp time psv : apnea lag ], if the Expiratory Pause is set by the GUI, | No common ambiguity cues detected. |
| CONT.37 | 0.00 | If the patient does not trigger a breath within the time of FUN.27 the apnea trigger window (apnea lag) the ventilator shall switch to PCV mode (apnea backup ventilation) with res- piratory rate RRAP , inspiratory pressure Pinsp AP , and the ratio between inspiratory time and Ex… | No common ambiguity cues detected. |
| CONT.39.1 | 0.00 | Pinsp PCV if current mode is PCV. FUN.20 | No common ambiguity cues detected. |
| CONT.39.2 | 0.00 | Pinsp PSV if current mode is PSV. FUN.24 | No common ambiguity cues detected. |
| CONT.39.3 | 0.00 | Pinsp AP if current model is PCV from apnea backup. FUN.27.2 | No common ambiguity cues detected. |
| CONT.4 | 0.00 | The transition from Self-Test Mode to VentilationOff FUN.6 Mode shall occur: | No common ambiguity cues detected. |
| CONT.4.1 | 0.00 | When the self-test procedure has successfully been com- FUN.6 pleted | No common ambiguity cues detected. |
| CONT.4.2 | 0.00 | When the GUI asks for resuming ventilation FUN.6 Ventilation Off : In ventilation off, the machine does not ventilate, the in valve is closed and the out valve is opened. | No common ambiguity cues detected. |
| CONT.41.1 | 0.00 | When the Inspiratory Pause is set by the GUI, the venti- FUN.29 lator shall wait for the end of the next inspiration phase, and if the Inspiratory Pause still required, both the inspi- ratory and expiratory valves will close until the inspira- tory pause is stopped by the GUI. | No common ambiguity cues detected. |
| CONT.42.1 | 0.00 | When the Expiratory Pause is set by the GUI, the ventila- FUN.28 tor shall wait for the end of the next expiration phase, and if the Expiratory Pause is still required, both the inspira- tory and expiratory valves will close until the expiratory pause is stopped by the GUI. | No common ambiguity cues detected. |
| CONT.43.1 | 0.00 | The controller shall stop RM if it is required from the GUI. Rationale: The GUI stops the RM if required by the user. | No common ambiguity cues detected. |
| CONT.45 | 0.00 | Before monitoring a sudden drop in pressure above the inhale trigger sensitivity ITS, the controller shall wait for the trigger window (0.7 sec). 42 5 Alarms ID Requirement / Rationale Input Ref. | No common ambiguity cues detected. |
| CONT.46 | 0.00 | The controller cannot return from fail-safe mode to any other mode without a power cycle (turn off and then turn on the machine). 36 4.1 Start-up Mode Final State (Stop Mode): In Final State the machine is turned off. | No common ambiguity cues detected. |
| CONT.5 | 0.00 | The transition from VentilationOff to PSV shall occur if the change mode command is received from the GUI. Rationale: the ventilation starts in PCV mode when the user selects the start command from the GUI. | No common ambiguity cues detected. |
| CONT.6 | 0.00 | The transition from VentilationOff to PCV mode shall occur if the change mode command is received from the GUI. Rationale: the ventilation starts in PSV mode when the user selects the start command from the GUI. PCV Mode: In PCV mode patients have no spontaneous respiration. | No common ambiguity cues detected. |
| CONT.7 | 0.00 | The transition from PCV to PSV shall occur if ventilation FUN.23 is on, the transition from PCV to PSV shall occur at the end of a PCV inspiratory time if the change mode command has been received from the GUI. Rationale: the doctor decides when the patient has some ability to b… | No common ambiguity cues detected. |
| CONT.8 | 0.00 | The transition from PCV to VentilationOff shall occur if the user stops the ventilation in PCV mode. Rationale: the ventilation stops when the user selects the stop command from the GUI. PSV Mode: In PSV mode patients are able to initiate every breath, and the machine supports t… | No common ambiguity cues detected. |
| CONT.9 | 0.00 | The transition from PSV to PCV shall occur if the patient FUN.27 does not trigger a breath within the time of the apnea trigger window. The switch shall occur with respiratory rate, target inspiratory pressure and I:E defined for the apnea backup mode. Rationale: the patient is … | No common ambiguity cues detected. |
| FUN.1 | 0.00 | The system shall provide ventilation support for patients who require mechanical ventilation and weigh more than 40 kg (88 lbs). Rationale: ventilation of children and infants is more chal- lenging | No common ambiguity cues detected. |
| FUN.10 | 0.00 | Once the start-up has been completed successfully the user must select “New Patient” or “Resume Ventilation” before the system transitions to self-test mode Rationale: in order to quickly resume ventilation for the same patient in case the unit had to be powered down 8 2.1 Funct… | No common ambiguity cues detected. |
| FUN.10.1 | 0.00 | If “New Patient” is selected, the user shall have to enter patient attributes and the completion of every step of the | No common ambiguity cues detected. |
| FUN.10.2 | 0.00 | If “Resume Ventilation” is selected, the system shall load the last calibration parameters, alarm thresholds, and ventilation parameters from the last active patient venti- lation. | No common ambiguity cues detected. |
| FUN.10.3 | 0.00 | If “Resume Ventilation” is selected, every step of the self- | No common ambiguity cues detected. |
| FUN.10.4 | 0.00 | Once all self-test steps have been completed successfully, it shall be possible to proceed to the Standby Mode. | No common ambiguity cues detected. |
| FUN.10.5 | 0.00 | In Standby Mode ventilation shall be off and it shall be possible to adjust all user-controlled parameters for ven- tilation and alarms before connecting to the patient and starting patient ventilation. | No common ambiguity cues detected. |
| FUN.10.6 | 0.00 | Once the power of the system has been off for more than 15 minutes it shall not be possible to select “Resume Ven- tilation” | No common ambiguity cues detected. |
| FUN.11 | 0.00 | The system shall connect to pressurized gas supply of oxygen and medical air and accept pressures up to 5.2 bar. Rationale: this covers the range of pressures available in hospital setting | No common ambiguity cues detected. |
| FUN.12 | 0.00 | The system shall provide breathing air through a stan- dard medical supply single-limbed patient circuit with a pneumatically controlled diaphragm expiration valve. Rationale: this is readily available medical supply | No common ambiguity cues detected. |
| FUN.13 | 0.00 | The system shall measure and display the breathing rate ISO 80601-2- (number of breathes per minute). 12 206.101 Rationale: observing and identifying the monitored venti- lation parameters is considered a primary operating func- tion | No common ambiguity cues detected. |
| FUN.14 | 0.00 | The system shall measure and display the percentage of ISO 80601-2- oxygen in the gas being delivered to the patient. 12 206.101 Rationale: observing and identifying the monitored venti- lation parameters is considered a primary operating func- tion | No common ambiguity cues detected. |
| FUN.15 | 0.00 | The system shall measure the ventilator pressure at/near ISO 80601- the inlet to the patient. 2-12 Rationale: observing and identifying the monitored venti- 201.12.4.102 lation parameters is considered a primary operating func- (b) tion | No common ambiguity cues detected. |
| FUN.16 | 0.00 | The system shall measure and display the volume of gas ISO 80601-2- delivered to the patient per breathing cycle (tidal vol- 12 ume). 206.101 Rationale: observing and identifying the monitored venti- lation parameters is considered a primary operating func- tion 9 2 System Requi… | No common ambiguity cues detected. |
| FUN.17 | 0.00 | The system shall measure and display the flow of gas de- ISO 80601-2- livered to the patient per breathing cycle. 12 Rationale: observing and identifying the monitored venti- 206.101 lation parameters is considered a primary operating func- tion | No common ambiguity cues detected. |
| FUN.18 | 0.00 | The system shall have a leak compensation feature for leaks in the patient breathing circuit which shall be dis- abled by default. Rationale: regulatory requirement | No common ambiguity cues detected. |
| FUN.18.1 | 0.00 | The user shall be able to disable/enable the leak compen- sation feature at any time. | No common ambiguity cues detected. |
| FUN.2 | 0.00 | The system shall provide pressure regulated ventilation controlling the inspiratory pressure. Rationale: pressure regulated ventilation is most benefi- cial for COVID-19 patients | No common ambiguity cues detected. |
| FUN.21.2 | 0.00 | A patient breath trigger shall reset the timer for the time- cycled breathing cycle. Rationale: Avoid breath stacking, which would lead to hyperventilation | No common ambiguity cues detected. |
| FUN.23.1 | 0.00 | When a PCV-to-PSV switch is initiated by the user the system shall ask the user for confirmation/setting of PSV parameters to be used | No common ambiguity cues detected. |
| FUN.23.2 | 0.00 | The switch to PSV shall occur only after the PSV parame- ters have been confirmed and until that has happened the PCV ventilation shall continue | No common ambiguity cues detected. |
| FUN.23.3 | 0.00 | The switch to PSV shall occur at the end of a PCV in- spiratory time 11 2 System Requirements | No common ambiguity cues detected. |
| FUN.24 | 0.00 | The system shall have a pressure support ventilation (PSV) mode, as characterized by the following plots of pressure and flow versus time. Rationale: In PSV mode, the ventilator supports the pa- tient who is supposed to breathe spontaneously. PSV is needed to wean patients off t… | No common ambiguity cues detected. |
| FUN.26 | 0.00 | In PSV mode the expiration phase shall start when the inspiratory flow drops below a setable fraction of the peak flow (Expiratory Trigger Setting) Rationale: Dropping inspiratory flow indicates the end of the inspiration | No common ambiguity cues detected. |
| FUN.27 | 0.00 | In PSV mode the system shall check for the presence of apnea, which occurs when a patient does not take new breath within the allowable apnea lag time. Rationale: In case the patient stops breathing (apnea) the system needs to be able to ensure that the patient continues to be v… | No common ambiguity cues detected. |
| FUN.27.2 | 0.00 | If apnea is detected, the system shall automatically switch from PSV to PCV mode with pre-determined apnea backup settings for RRAP , Pinsp AP , I:EAP . RRAP and Pinsp AP shall be set by the user. I:EAP will be fixed at 1:2 Rationale: in case of apnea the ventilator needs to tak… | No common ambiguity cues detected. |
| FUN.3 | 0.00 | The system shall provide positive end expiratory pressure (PEEP) ventilation. Rationale: PEEP is important to keep alveoli recruited at the end of expiration | No common ambiguity cues detected. |
| FUN.30 | 0.00 | The high-level operation sequence shall follow the scheme shown in Figure 2.1 2.1.1 Safety Related Functional Requirements This section covers the functional requirements imposed on the system based on the initiating events necessitating mitigating functions. ID Requirement / Ra… | No common ambiguity cues detected. |
| FUN.31 | 0.00 | Any normal operating mode or identified failure mode of ISO 80601-2- the system and its components shall always result in a 12 state of the system that is safe for the patient. Rationale: patient safety is primary concern | No common ambiguity cues detected. |
| FUN.32 | 0.00 | In a worst-case failure, the controller shall leave the sys- ISO 80601-2- tem in a state that allows the patient to inhale and exhale 12 unimpeded. 201.13.2.103 Rationale: patient safety is primary concern | No common ambiguity cues detected. |
| FUN.33 | 0.00 | Any power failure shall leave the system in a state ISO 80601-2- that allows the patient to inhale and exhale unimpeded. 12 Rationale: patient safety is primary concern | No common ambiguity cues detected. |
| FUN.34 | 0.00 | Any failure of the gas supply shall leave the system in a state that allows the patient to inhale and exhale unim- peded. Rationale: patient safety is primary concern | No common ambiguity cues detected. |
| FUN.35 | 0.00 | The system shall prevent airborne contaminants (partic- ulate, viral, bacterial) being delivered from the ventilator to the patient. Rationale: patient safety is primary concern | No common ambiguity cues detected. |
| FUN.36 | 0.00 | The system shall prevent patient expiratory viral and bac- terial contaminants from entering the atmosphere. Rationale: need to ensure that clinicians and other pa- tients are not exposed | No common ambiguity cues detected. |
| FUN.37 | 0.00 | The system shall have an internal power source that al- ISO 80601-2- lows operation for 120 minutes past the failure of the ex- 12 ternal power source. Rationale: regulatory requirement | No common ambiguity cues detected. |
| FUN.38 | 0.00 | The system shall have clearly ranked (high/medium/low ISO 60601-1-8 priority) visual alarms. Rationale: regulatory requirement Rationale: regulatory requirement | No common ambiguity cues detected. |
| FUN.38.1 | 0.00 | The system shall raise an alarm when a parameter value goes outside the range defined for its associated alarm. | No common ambiguity cues detected. |
| FUN.39 | 0.00 | The system shall prompt the user before ventilation is started to enter user-controlled alarm thresholds for | No common ambiguity cues detected. |
| FUN.40 | 0.00 | The system shall react to the inspiratory airway pressure | No common ambiguity cues detected. |
| FUN.41 | 0.00 | A failure of the GUI (e.g. GUI freezes) or a loss of commu- nication between the GUI and the Controller shall raise a high-priority alarm and any ongoing ventilation process shall not be interrupted. Rationale: in order to keep the patient safe, the venti- lation needs to contin… | No common ambiguity cues detected. |
| FUN.42 | 0.00 | The communication between Controller and GUI shall be reliable. 2.2 Measured and displayed parameters ID Requirement / Rationale Input Ref. The system shall measure and display the following values for the patient: Rationale: regulatory requirement | No common ambiguity cues detected. |
| FUN.43 | 0.00 | Respiratory rate (RR) of the patient measured by the ventilator, in units of breaths per minute (bpm). | No common ambiguity cues detected. |
| FUN.44 | 0.00 | Peak inspiratory pressure (Peak Pinsp ) measured for the most recent breath. | No common ambiguity cues detected. |
| FUN.45 | 0.00 | Positive end expiratory pressure (PEEP) measured (in cmH2O) for the most recent breath. | No common ambiguity cues detected. |
| FUN.46 | 0.00 | Tidal volume (Vtidal) measured for the most recent breath (in mL). | No common ambiguity cues detected. |
| FUN.47 | 0.00 | Minute volume (VE) measured (in slpm) by the ventila- tor. | No common ambiguity cues detected. |
| FUN.48 | 0.00 | Fraction of inspired oxygen (FiO2). | No common ambiguity cues detected. |
| FUN.48.1 | 0.00 | The user shall set the desired FiO2 value from which the +-3% alarm limits are derived. The input FiO2 value will have to be manually adjusted by the user until the desired FiO2 value is displayed. Indication in waveform Rationale: regulatory requirement | No common ambiguity cues detected. |
| FUN.49 | 0.00 | Instantaneous airway pressure (PAW), measured in cmH2O. | No common ambiguity cues detected. |
| FUN.5.3 | 0.00 | The patient shall not be connected to the breathing cir- cuit when the system is powered on and through start-up and self-test. | No common ambiguity cues detected. |
| FUN.50 | 0.00 | Instantaneous flow, measured in slpm. | No common ambiguity cues detected. |
| FUN.51 | 0.00 | Instantaneous tidal volume (Vtidal), measured in mL. Parameters set by the user Rationale: regulatory requirement | No common ambiguity cues detected. |
| FUN.52 | 0.00 | Ratio of Inspiratory time to Expiratory time (I:E). | No common ambiguity cues detected. |
| FUN.53 | 0.00 | Maximum inspiratory pressure (Max Pinsp) Indication of the machine status Rationale: regulatory requirement | No common ambiguity cues detected. |
| FUN.54 | 0.00 | Level of battery, i.e., the percentage of battery remain- ing. | No common ambiguity cues detected. |
| FUN.55 | 0.00 | Power source: if the system is receiving power from the main supply, or if it is running on backup battery power. | No common ambiguity cues detected. |
| FUN.56 | 0.00 | Value of the temperature inside the system unit is re- ported. | No common ambiguity cues detected. |
| FUN.57 | 0.00 | Current status of the system (running/stopped, PCV/PSV) is reported. | No common ambiguity cues detected. |
| FUN.58 | 0.00 | The remaining time for RM is displayed. 2.3 Values and ranges 2.3.1 Common values and ranges 15 2 System Requirements ID Requirement / Rationale Input Ref. | No common ambiguity cues detected. |
| FUN.59 | 0.00 | Unable to read gas flow to patient 201.13.2.104 | No common ambiguity cues detected. |
| FUN.6.1 | 0.00 | The self-test procedure shall confirm the switchover from external to internal power works. | No common ambiguity cues detected. |
| FUN.6.2 | 0.00 | The self-test procedure shall confirm there are no unac- ceptable leaks in the breathing circuit. | No common ambiguity cues detected. |
| FUN.6.3 | 0.00 | The self-test procedure shall confirm the FI2 flow meter (see Figure 2.2) in the patient breathing circuit is con- nected in the right direction and is calibrated. | No common ambiguity cues detected. |
| FUN.6.4 | 0.00 | The self-test procedure shall confirm the expiratory valve is functional. | No common ambiguity cues detected. |
| FUN.6.5 | 0.00 | The self-test procedure shall confirm the oxygen sensor is calibrated. | No common ambiguity cues detected. |
| FUN.6.6 | 0.00 | The self-test procedure shall confirm the local alarms are functional. | No common ambiguity cues detected. |
| FUN.8 | 0.00 | The system shall log key parameters, save them before being powered off and load them upon start-up to be made available on a log page on the GUI. | No common ambiguity cues detected. |
| FUN.8.1 | 0.00 | The system shall have means to indicate visually the cu- ISO 80601-2- mulative hours of operation of the ventilator, either 1) 12: 201.104 automatically; or 2) by operator action. 7 2 System Requirements | No common ambiguity cues detected. |
| FUN.8.10 | 0.00 | The system shall provide a log to include results of the ISO 80601- pre-use check. 2-12: 208.6.12.101 | No common ambiguity cues detected. |
| FUN.8.11 | 0.00 | The system shall provide a log to include the overall du- ration of the active use of the O2 sensor (%-hours) Rationale: The oxygen sensor has a limited lifetime ex- pectancy requiring a monitoring of its use in order to track its deterioration over time | No common ambiguity cues detected. |
| FUN.8.12 | 0.00 | The system shall log user-set ventilation and alarm pa- rameters as well as the current calibration parameters. Rationale: user-set ventilation and alarm parameters need to be able for the resumption of ventilation in case the system has to be briefly turned off. | No common ambiguity cues detected. |
| FUN.8.3 | 0.00 | The system shall time stamp all alarm events either via ISO 80601- the date and time, the elapsed time since the occurrence 2-12: of the alarm condition, or the elapsed time from the start 208.6.12.101 of use of system | No common ambiguity cues detected. |
| FUN.8.4 | 0.00 | The system shall not lose the contents of the alarm system ISO 80601- log during a loss of power for less than 7 d unless erased 2-12: by authorized personnel of the hospital. 208.6.12.101 | No common ambiguity cues detected. |
| FUN.8.5 | 0.00 | The system shall not permit the healthcare professional ISO 80601- operator to erase the contents of the alarm system log. 2-12: 208.6.12.101 | No common ambiguity cues detected. |
| FUN.8.6 | 0.00 | The system shall provide a log to include any change of ISO 80601- ventilator settings, including the value applied 2-12: 208.6.12.101 | No common ambiguity cues detected. |
| FUN.8.7 | 0.00 | The system shall provide a log to include any change of ISO 80601- alarm settings, including the value applied 2-12: 208.6.12.101 | No common ambiguity cues detected. |
| FUN.8.8 | 0.00 | The system shall provide a log to include change of pa- ISO 80601- tient, including the patient attributes; 2-12: 208.6.12.101 | No common ambiguity cues detected. |
| FUN.8.9 | 0.00 | The system shall provide a log to include power supply ISO 80601- source change, including the source utilized 2-12: 208.6.12.101 | No common ambiguity cues detected. |
| FUN.9 | 0.00 | Once the self-test has been completed successfully and configurations have been loaded properly the system shall start monitoring and reporting health parameters. Rationale: At this point the monitoring module is able to carry out its assigned functionality. | No common ambiguity cues detected. |
| GUI.1 | 0.00 | GUI shall implement the following modes (see Figure 3.1): FUN.4 | No common ambiguity cues detected. |
| GUI.1.1 | 0.00 | Start-up Mode: In start-up mode the GUI initializes itself with default configuration parameters. Start-up mode is completed once start-up parameter validation and initial- ization have been completed. | No common ambiguity cues detected. |
| GUI.1.2 | 0.00 | , GUI.1.3, GUI.5, GUI.7,Start Mode: allows the user to resume ventilation or to start the ventilation for a new patient. | No common ambiguity cues detected. |
| GUI.1.3 | 0.00 | Menu Mode: allows the user to set parameters and start the ventilation. | No common ambiguity cues detected. |
| GUI.1.4 | 0.00 | Self Test Mode: allows the user to perform a sequence of tests. | No common ambiguity cues detected. |
| GUI.1.5 | 0.00 | Ventilation Mode: the GUI is monitoring and controlling the ventilation of the patient. Start-up Mode: In start-up mode the GUI initializes itself with default configuration param- eters. | No common ambiguity cues detected. |
| GUI.10 | 0.00 | andIf the Self Test fails, the GUI is blocked with a message, FUN.7 and the user shall obtain a replacement of the unit and tag the problematic unit for a maintenance inspection. | No common ambiguity cues detected. |
| GUI.100 | 0.00 | Target inspiratory pressure (Pinsp AP ) PER.14 Control Settings in RM | No common ambiguity cues detected. |
| GUI.101 | 0.00 | Pressure for the Recruitment Maneuver (PRM) PER.3.1 | No common ambiguity cues detected. |
| GUI.102 | 0.00 | Timer RM (Time for Recruitment Maneuver). PER.3.2 3.8 Frozen Mode ID Requirement / Rationale Input Ref. | No common ambiguity cues detected. |
| GUI.104 | 0.00 | The user shall be able to quit the frozen mode. | No common ambiguity cues detected. |
| GUI.105 | 0.00 | The ventilation shall continue uninterrupted when Frozen Mode is enabled. 3.9 Alarm settings Mode ID Requirement / Rationale Input Ref. | No common ambiguity cues detected. |
| GUI.106 | 0.00 | During Alarm setting mode, the user shall be able to PER.15 | No common ambiguity cues detected. |
| GUI.107 | 0.00 | The user shall be able to select the parameters they want to display in Show RealTime Data Mode and their order. 33 3 GUI Requirements Figure 3.2: Draft of GUI 34 4 Controller Requirements powerOff error powerOff error FailSafe error startPCV PCV error stopVentilation apneaLag p… | No common ambiguity cues detected. |
| GUI.109 | 0.00 | The transition from Settings to Ventilation shall occur if the ventilator is ventilating and the operator has finished setting the parameters. | No common ambiguity cues detected. |
| GUI.11 | 0.00 | The GUI shall be able to test the communication with FUN.5.1 the Controller. 26 3.1 Start-up Mode | No common ambiguity cues detected. |
| GUI.110 | 0.00 | The transition from Settings to Menu shall occur if the ventilation is off and the operator has finished setting the parameters. 3.1 Start-up Mode ID Requirement / Rationale Input Ref. | No common ambiguity cues detected. |
| GUI.111 | 0.00 | , GUI.112, GUI.113The GUI shall be able to initialize itself with default con- figuration parameters if the controller is not ventilating. | No common ambiguity cues detected. |
| GUI.112 | 0.00 | The GUI shall remain in Self-Test mode if the user inter- rupts the self-test procedure. | No common ambiguity cues detected. |
| GUI.113 | 0.00 | The GUI shall remain in Self-Test mode if the self-test has been interrupted and the user runs again the self-test procedure. Ventilation Mode: During Ventilation mode, the patient is ventilated. | No common ambiguity cues detected. |
| GUI.12 | 0.00 | If the Controller is not ventilating, when start-up is fin- FUN.4 ished, the GUI shall be able to move to Start Mode. | No common ambiguity cues detected. |
| GUI.13.1 | 0.00 | The GUI shall be able to update parameter settings with values read from the Controller. | No common ambiguity cues detected. |
| GUI.13.3 | 0.00 | The ventilation is assumed to be running (the GUI is showing that ventilation is in progress). | No common ambiguity cues detected. |
| GUI.14 | 0.00 | The GUI shall be able to check system memory. FUN.5.1 | No common ambiguity cues detected. |
| GUI.16 | 0.00 | Respiratory Rate (RRPCV ) PER.4 | No common ambiguity cues detected. |
| GUI.17 | 0.00 | I:E Ratio (I:EPCV ) PER.5 | No common ambiguity cues detected. |
| GUI.18 | 0.00 | Target inspiratory pressure (Pinsp PCV ) PER.6 | No common ambiguity cues detected. |
| GUI.19 | 0.00 | Inhale trigger sensitivity (ITSPCV ) PER.7 PSV mode | No common ambiguity cues detected. |
| GUI.20 | 0.00 | Target inspiratory pressure (Pinsp PSV ) PER.8 | No common ambiguity cues detected. |
| GUI.21 | 0.00 | Inhale trigger sensitivity (ITSPSV ) PER.9 | No common ambiguity cues detected. |
| GUI.22 | 0.00 | Expiratory trigger sensitivity (ETS) PER.10 | No common ambiguity cues detected. |
| GUI.23 | 0.00 | Apnea lag PER.11 Apnea backup | No common ambiguity cues detected. |
| GUI.24 | 0.00 | Respiratory Rate (RRAP ) PER.12 | No common ambiguity cues detected. |
| GUI.25 | 0.00 | I:E Ratio (I:EAP ) PER.13 | No common ambiguity cues detected. |
| GUI.26 | 0.00 | Target inspiratory pressure (Pinsp AP ) PER.14 Alarm thresholds | No common ambiguity cues detected. |
| GUI.27 | 0.00 | Max Pinsp PER.15 Rationale: Max Pinsp is the maximum value for PAW be- fore the alarm is generated | No common ambiguity cues detected. |
| GUI.28 | 0.00 | Min Pinsp PER.16 Rationale: Min Pinsp is the minimum value for PAW be- fore the alarm is generated | No common ambiguity cues detected. |
| GUI.29 | 0.00 | Max Vtidal exp PER.19 27 3 GUI Requirements | No common ambiguity cues detected. |
| GUI.30 | 0.00 | Min Vtidal exp PER.20 | No common ambiguity cues detected. |
| GUI.31 | 0.00 | Max Vtidal insp PER.19 | No common ambiguity cues detected. |

## Inconsistency candidates

| Left | Right | Similarity | Kind | Evidence |
|---|---|---:|---|---|
| AL.11 | AL.14.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.15.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.15.2 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.15.3 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.15.4 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.18 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.19 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.11 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.15 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.20 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.22 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.25 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.26 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.27 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.38 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.39 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.40 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.41 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | AL.43 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14 | FUN.59 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.15.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.15.2 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.15.3 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.15.4 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.18 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.19 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.1 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.14.2 | AL.14.3 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.20 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.22 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.25 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.26 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.27 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.38 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.39 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.40 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.41 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | AL.43 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15 | FUN.59 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.15.2 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.15.3 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.15.4 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.18 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.19 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.1 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.15.3 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.15.4 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.18 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.19 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.2 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.15.4 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.18 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.19 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.3 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.18 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.19 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.15.4 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.16.1 | AL.21.2 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.16.1 | AL.26.2 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.16.1 | AL.42.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.16.1 | AL.43.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.16.1 | AL.44.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.16.1 | AL.45.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.19 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.18 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.23 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.19 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.22 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.25 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.26 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.27 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.38 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.39 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.40 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.41 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | AL.43 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.20 | FUN.59 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.21.2 | AL.26.2 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.21.2 | AL.42.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.21.2 | AL.43.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.21.2 | AL.44.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.21.2 | AL.45.1 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.25 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.26 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.27 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.38 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.39 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.40 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.41 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | AL.43 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.22 | FUN.59 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.24 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.23 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.28 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.29 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.30 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.31 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.32 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.33 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.34 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.35 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.24 | AL.36 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | AL.26 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | AL.27 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | AL.38 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | AL.39 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | AL.40 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | AL.41 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | AL.43 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.25 | FUN.59 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.26 | AL.27 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.26 | AL.38 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| AL.26 | AL.39 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
