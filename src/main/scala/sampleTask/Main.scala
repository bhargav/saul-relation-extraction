package sampleTask

object Main extends App {

  // Populate data
  val trainingData = Seq(
    SampleDocument("This is a ham", "Ham"),
    SampleDocument("Another ham document", "Ham"),
    SampleDocument("Some spam looks like this", "Spam"),
    SampleDocument("This is also a spam", "Spam")
  )

  val testingData = Seq(
    SampleDocument("Spam Ho", "Spam"),
    SampleDocument("Yet another ham document", "Ham"),
    SampleDocument("Yet another another ham document", "Ham")
  )

  SampleDataModel.documents.populate(trainingData)
  SampleDataModel.documents.populate(testingData, train = false)

  // Train Classifier
  SampleClassifier.learn(30)

  // Test Classifier
  SampleClassifier.test()
}