/** This software is released under the University of Illinois/Research and Academic Use License. See
  * the LICENSE file in the root folder for details. Copyright (c) 2016
  *
  * Developed by: The Cognitive Computations Group, University of Illinois at Urbana-Champaign
  * http://cogcomp.cs.illinois.edu/
  */
package org.cogcomp.SaulRelationExtraction

import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent
import edu.illinois.cs.cogcomp.illinoisRE.data.SemanticRelation
import edu.illinois.cs.cogcomp.lbjava.learn.{SparseNetworkLearner, SupportVectorMachine}
import edu.illinois.cs.cogcomp.saul.classifier.Learnable
import edu.illinois.cs.cogcomp.saul.datamodel.property.Property

import org.cogcomp.SaulRelationExtraction.REDataModel._

object REClassifiers {
  var useRelationBrownFeatures = false

  private val mentionFeatures = List(
    //      NoPrepFeature, OnePrepFeature, TwoPrepFeature, MoreThanTwoPrepFeature, NoVerbFeature, NoCommaFeature,
    InOrgPoliticalListFeature, InOrgTerroristListFeature,
    InVehListFeature,
    InWeaListFeature,
    InFacBarrierListFeature, InFacBuildingListFeature, InFacConduitListFeature, InFacPathListFeature, InFacPlantListFeature,
    InFacBuildingSubAreaListFeature, InFacGenericListFeature,
    //      SurroundingWordsFeature,
    WordSequenceFeature, POSSequenceFeature, WordAndPOSSequenceFeature,
    InPersonListFeature,
    //      InGPEListFeature,
    InEthnicGroupOrNationalityListFeature,
    InGPECityListFeature, InGPECountryListFeature, InGPECountyListFeature, InGPEStateListFeature, InGPECommonNounListFeature,
    InGPEMajorAreaListFeature,
    InOrgGovtListFeature, InOrgCommercialListFeature, InOrgEducationalListFeature,
    //      InPersonTitleListFeature, InPersonNameListFeature, InPersonPronounListFeature, InPersonDBpediaListFeature,
    //      SynOfAllNounFeature,
    mentionTypeFeatures
  //      POSIndexBagFeature, WordIndexBagFeature, POSWordIndexBagFeature, POSEndWordIndexBagFeature,
  //      WordBCIndexBagFeature, POSWordBCIndexBagFeature, POSEndWordBCIndexBagFeature,
  //      ParseExactFeature, ParseCoverFeature,
  //      ContextLeftWordFeature, ContextLeftPOSFeature, ContextRightWordFeature, ContextRightPOSFeature,
  //      WikiAttributesFeature,
  //      NERLabelsFeature
  )

  private val relationFeatures: List[Property[SemanticRelation]] = List(
    M1MostConfidentMainTypeFeature, M2MostConfidentMainTypeFeature,
    M1MostConfidentSubTypeFeature, M2MostConfidentSubTypeFeature,
    M1LevelMainTypeFeature, M2LevelMainTypeFeature,
    M1LevelSubTypeFeature, M2LevelSubTypeFeature,
    M1MainType_m1IncludesM2Feature, M2MainType_m1IncludesM2Feature,
    M1MainType_m2IncludesM1Feature, M2MainType_m2IncludesM1Feature,
    M1SubType_m1IncludesM2Feature, M2SubType_m1IncludesM2Feature,
    M1SubType_m2IncludesM1Feature, M2SubType_m2IncludesM1Feature,

    //      M1WikiAttributesFeature, M2WikiAttributesFeature,

    WordBetweenNullFeature,
    M1IncludesM2Feature, M2IncludesM1Feature,
    BowM1Feature, BowM2Feature,
    HwM1Feature, HwM2Feature,
    //      LhwM1Feature, HwM1RFeature, LhwM2Feature, HwM2RFeature, LLhwM1Feature, LhwM1RFeature, HwM1RRFeature, LLhwM2Feature, LhwM2RFeature, HwM2RRFeature,
    HwM1M2Feature,
    WordBetweenSingleFeature, WordBetweenFirstFeature, WordBetweenLastFeature, WordBetweenBOWFeature,
    BigramsInBetweenFeature,
    M1M2MostConfidentMainTypeFeature,
    M1M2MostConfidentSubTypeFeature,
    M1M2MentionLevelFeature,
    M1LevelMainTypeAndm2LevelMainTypeFeature,
    M1LevelSubTypeAndm2LevelSubTypeFeature,
    M1m2SubType_m1IncludesM2Feature, M1m2SubType_m2IncludesM1Feature,
    M1m2MainType_m1IncludesM2Feature, M1m2MainType_m2IncludesM1Feature,
    M1HeadWordAndDepParentWordFeature, M2HeadWordAndDepParentWordFeature,
    DepPathInBetweenFeature,
    DepLabelsInBetweenFeature,
    CpInBetweenNullFeature,
    //      PofM1HwFeature, PofM2HwFeature,
    PosBetweenSingleFeature,
    PM1aPM2Feature, M1PaM2PFeature, PPM1aPPM2Feature, PM1PaPM2PFeature, M1PPaM2PPFeature,
    //      PbeforeM1HeadFeature, PafterM1HeadFeature, PbeforeM2HeadFeature, PafterM2HeadFeature,
    M1DepLabelFeature, M2DepLabelFeature,
    //      BagOfChunkTypesInBetweenFeature,
    //      M1IsNationalityFeature, M2IsNationalityFeature,
    //      PreModIsPartOfWikiTitleFeature,
    //      PremodIsWordNetNounCollocationFeature,
    //      HasCommonVerbSRLPredicateFeature,
    //       FirstDepLabelInBetweenFeature, LastDepLabelInBetweenFeature,
    //
    //      	OnePrepInBetweenFeature, TwoPrepInBetweenFeature, MoreThanTwoPrepInBetweenFeature,
    //      	SinglePrepStringInBetweenFeature, FirstPrepStringInBetweenFeature, LastPrepStringInBetweenFeature,

    //      FeaturesOfFirstPrep,
    //      FeaturesOfSecondPrep,
    //      FeaturesOfLastPrep,
    OnlyPrepInDepPathFeature, ApposInDepPathFeature,
    SmallerMentionIsPerTitleFeature,
    HasCoveringMentionFeature
  )

  private val relationBrownClusterFeatures: List[Property[SemanticRelation]] = List(
    BowM1bc10Feature, BowM2bc10Feature,
    HwM1bc10Feature, HwM2bc10Feature,
    LhwM1bc10Feature, HwM1Rbc10Feature, LhwM2bc10Feature, HwM2Rbc10Feature, LLhwM1bc10Feature, LhwM1Rbc10Feature,
    HwM1RRbc10Feature, LLhwM2bc10Feature, LhwM2Rbc10Feature, HwM2RRbc10Feature,
    PM1aPM2bc10Feature, M1PaM2Pbc10Feature, PPM1aPPM2bc10Feature, PM1PaPM2Pbc10Feature, M1PPaM2PPbc10Feature,
    HwM1M2bc10Feature,
    WordBetweenSinglebc10Feature, WordBetweenFirstbc10Feature, WordBetweenLastbc10Feature,
    M1HeadWordAndDepParentWordbc10Feature, M2HeadWordAndDepParentWordbc10Feature,
    SinglePrepStringInBetweenbc10Feature, FirstPrepStringInBetweenbc10Feature, LastPrepStringInBetweenbc10Feature
  )

  private val relationStructuralFeatures: List[Property[SemanticRelation]] = List(
    MatchNestedPatternFeature,
    MatchPreModPatternFeature,
    MatchPossesivePatternFeature,
    MatchPrepositionPatternFeature,
    MatchFormulaicPatternFeature
  )

  private val relationWord2VecFeatures: List[Property[SemanticRelation]] = List(
    M1M2SubtypeAndCommonAncestorW2VFeature,
    M1HWw2vFeature,
    M2HWw2vFeature,
    M1HWw2vcFeature,
    M2HWw2vcFeature,
    HWw2vSubtractionFeature,
    WordBetweenSingleW2VFeature,
    WordBetweenSingleW2vcFeature,
    WordBetweenFirstW2VFeature,
    WordBetweenFirstW2vcFeature,
    WordBetweenLastW2VFeature,
    WordBetweenLastW2vcFeature,
    M1DepParentWordW2vFeature,
    M1DepParentWordW2vcFeature,
    M2DepParentWordW2vFeature,
    M2DepParentWordW2vcFeature,
    WordAfterM1W2VFeature,
    WordAfterM1W2vcFeature,
    WordBeforeM2W2VFeature,
    WordBeforeM2W2vcFeature,
    SingleWordBetweenMentionsW2vFeature
  )

  object mentionTypeFineClassifier extends Learnable[Constituent](tokens) {
    def label = mentionFineLabel
    override def feature = using(mentionFeatures)
    override lazy val classifier = new SupportVectorMachine(1, 0.1, -1, "L2LOSS_SVM_DUAL")
  }

  object mentionTypeCoarseClassifier extends Learnable[Constituent](tokens) {
    def label = mentionCoarseLabel
    override def feature = using(mentionFeatures)
    override lazy val classifier = new SupportVectorMachine(1, 0.1, -1, "L2LOSS_SVM_DUAL")
  }

  object relationTypeFineClassifier extends Learnable[SemanticRelation](pairedRelations) {
    def label = relationFineLabel
    override def feature = if (useRelationBrownFeatures) using(relationFeatures ::: relationBrownClusterFeatures ::: relationStructuralFeatures ::: relationWord2VecFeatures) else using(relationFeatures)
    override lazy val classifier = new SparseNetworkLearner()
  }

  object relationTypeCoarseClassifier extends Learnable[SemanticRelation](pairedRelations) {
    override def label = relationCoarseLabel
    override def feature = if (useRelationBrownFeatures) using(relationFeatures ::: relationBrownClusterFeatures ::: relationStructuralFeatures ::: relationWord2VecFeatures) else using(relationFeatures)
    override lazy val classifier = new SparseNetworkLearner()
  }
}
