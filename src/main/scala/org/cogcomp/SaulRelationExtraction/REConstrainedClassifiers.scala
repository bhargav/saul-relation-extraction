/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computations Group, University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
package org.cogcomp.SaulRelationExtraction

import edu.illinois.cs.cogcomp.illinoisRE.data.SemanticRelation

import edu.illinois.cs.cogcomp.saul.classifier.infer.ConstrainedClassifier
import edu.illinois.cs.cogcomp.saul.classifier.infer.solver.OJAlgo
import edu.illinois.cs.cogcomp.saul.lbjrelated.LBJLearnerEquivalent

import org.cogcomp.SaulRelationExtraction.REClassifiers._

object REConstrainedClassifiers {
  object relationHierarchyConstrainedClassifier extends ConstrainedClassifier[SemanticRelation, SemanticRelation] {
    override def onClassifier: LBJLearnerEquivalent = relationTypeFineClassifier
    override def subjectTo = Some(REConstraints.relationHierarchyConstraint)
    override def solverType = OJAlgo
  }
}
