package com.github.vitalsoftware.scalaredox.models

trait HasPatient {
  def Patient: Patient
}

trait HasClinicalSummaryPatient {
  def Patient: ClinicalSummaryPatient
}
