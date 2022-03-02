package com.github.vitalsoftware.scalaredox.models

trait HasPatient {
  def Patient: Patient
}

trait HasMergedPatient {
  def Patient: MergedPatient
}
