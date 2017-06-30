/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computations Group, University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
package org.cogcomp.SaulRelationExtraction

import edu.illinois.cs.cogcomp.illinoisRE.data.SemanticRelation
import edu.illinois.cs.cogcomp.saul.classifier.infer.Constraint._
import edu.illinois.cs.cogcomp.saul.classifier.infer.{ Constraint, PairConjunction }
import org.cogcomp.SaulRelationExtraction.REClassifiers._

object REConstraints {

  //      Map for tracking the Coarse-Fine relationship hierarchy
  private val relationHierarchy: Map[String, List[String]] = Map(
    ("NO_RELATION", List("NO_RELATION")),
    ("m1-ART-m2", List("m1-ART:Inventor-or-Manufacturer-m2", "m1-ART:User-or-Owner-m2")),
    ("m1-EMP-ORG-m2", List("m1-EMP-ORG:Employ-Executive-m2", "m1-EMP-ORG:Employ-Staff-m2",
      "m1-EMP-ORG:Employ-Undetermined-m2", "m1-EMP-ORG:Member-of-Group-m2", "m1-EMP-ORG:Other-m2",
      "m1-EMP-ORG:Partner-m2", "m1-EMP-ORG:Subsidiary-m2")),
    ("m1-GPE-AFF-m2", List("m1-GPE-AFF:Based-In-m2", "m1-GPE-AFF:Citizen-or-Resident-m2", "m1-GPE-AFF:Other-m2")),
    ("m1-OTHER-AFF-m2", List("m1-OTHER-AFF:Ethnic-m2", "m1-OTHER-AFF:Ideology-m2", "m1-OTHER-AFF:Other-m2")),
    ("m1-PER-SOC-m2", List("m1-PER-SOC:Business-m2", "m1-PER-SOC:Family-m2", "m1-PER-SOC:Other-m2")),
    ("m1-PHYS-m2", List("m1-PHYS:Located-m2", "m1-PHYS:Near-m2", "m1-PHYS:Part-Whole-m2")),

    ("m2-ART-m1", List("m2-ART:Inventor-or-Manufacturer-m1", "m2-ART:User-or-Owner-m1")),
    ("m2-EMP-ORG-m1", List("m2-EMP-ORG:Employ-Executive-m1", "m2-EMP-ORG:Employ-Staff-m1",
      "m2-EMP-ORG:Employ-Undetermined-m1", "m2-EMP-ORG:Member-of-Group-m1", "m2-EMP-ORG:Other-m1",
      "m2-EMP-ORG:Partner-m1", "m2-EMP-ORG:Subsidiary-m1")),
    ("m2-GPE-AFF-m1", List("m2-GPE-AFF:Based-In-m1", "m2-GPE-AFF:Citizen-or-Resident-m1", "m2-GPE-AFF:Other-m1")),
    ("m2-OTHER-AFF-m1", List("m2-OTHER-AFF:Ethnic-m1", "m2-OTHER-AFF:Ideology-m1", "m2-OTHER-AFF:Other-m1")),
    ("m2-PER-SOC-m1", List("m2-PER-SOC:Business-m1", "m2-PER-SOC:Family-m1", "m2-PER-SOC:Other-m1")),
    ("m2-PHYS-m1", List("m2-PHYS:Located-m1", "m2-PHYS:Near-m1", "m2-PHYS:Part-Whole-m1"))
  )

  /** Constraint enforcing coarse-fine relation hierarchy on relations */
  val relationHierarchyConstraint = REDataModel.pairedRelations.ForEach({
    rel: SemanticRelation =>
      relationHierarchy.map({
        case (coarseLabel, fineLabelList) =>
          // We generate the `implication` statement for each coarse label by constraining the fine labels
          // to occur from the child relations only.
          (relationTypeCoarseClassifier on rel is coarseLabel) ==>
            fineLabelList.map((relationTypeFineClassifier on rel) is _).reduce[Constraint[_]](_ or _)
      }).reduce[Constraint[_]](_ and _)
  })

  private val relationToFirstEntityMapping: Map[String, (List[String], List[String])] = Map(
    ("m1-ART-m2", (List("GPE", "ORG", "PER"), List("FAC", "GPE", "VEH", "WEA"))),
    ("m1-EMP-ORG-m2", (List("GPE", "ORG", "PER"), List("GPE", "ORG", "PER"))),
    ("m1-GPE-AFF-m2", (List("GPE", "ORG", "PER"), List("GPE", "LOC"))),
    ("m1-OTHER-AFF-m2", (List("GPE", "ORG", "PER"), List("GPE", "LOC"))),
    ("m1-PER-SOC-m2", (List("PER"), List("PER"))),

    ("m2-ART-m1", (List("GPE", "ORG", "PER"), List("FAC", "GPE", "VEH", "WEA"))),
    ("m2-EMP-ORG-m1", (List("GPE", "ORG", "PER"), List("GPE", "ORG", "PER"))),
    ("m2-GPE-AFF-m1", (List("GPE", "ORG", "PER"), List("GPE", "LOC"))),
    ("m2-OTHER-AFF-m1", (List("GPE", "ORG", "PER"), List("GPE", "LOC"))),
    ("m2-PER-SOC-m1", (List("PER"), List("PER")))
  )

  val entityTypeArgumentConstraint = REDataModel.pairedRelations.ForEach({
    rel: SemanticRelation =>
      relationToFirstEntityMapping.map({
        case (coarseLabel, (firstEntityLabels, secondEntityLabels)) =>
          (relationTypeCoarseClassifier on rel is coarseLabel) ==>
            PairConjunction(
              firstEntityLabels.map((mentionTypeCoarseClassifier on RESensors.relationToFirstMention(rel)) is _).reduce[Constraint[_]](_ or _),
              secondEntityLabels.map((mentionTypeCoarseClassifier on RESensors.relationToSecondMention(rel)) is _).reduce[Constraint[_]](_ or _)
            )
      }).reduce[Constraint[_]](_ and _)
  })
}
