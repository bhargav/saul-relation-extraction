package sampleTask

import edu.illinois.cs.cogcomp.saul.datamodel.DataModel

import scala.collection.JavaConversions._

case class SampleDocument(sentence: String, label: String)

object SampleDataModel extends DataModel {
  val documents = node[SampleDocument]

  val docLabel = property(documents) {
    doc: SampleDocument => doc.label
  }

  val words = property(documents) {
    doc: SampleDocument => doc.sentence.split("\\s+").map(_.toLowerCase).toList
  }

  val bigrams = property(documents) {
    doc: SampleDocument => words(doc).sliding(2).map(_.mkString("-")).toList
  }
}