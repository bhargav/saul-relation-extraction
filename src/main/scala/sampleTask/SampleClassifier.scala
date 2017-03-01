package sampleTask

import edu.illinois.cs.cogcomp.lbjava.learn.SparseNetworkLearner

import edu.illinois.cs.cogcomp.saul.classifier.Learnable

import sampleTask.SampleDataModel._

object SampleClassifier extends Learnable(documents) {
  def label = docLabel
  override lazy val classifier = new SparseNetworkLearner()
  override def feature = using(words, bigrams)
}