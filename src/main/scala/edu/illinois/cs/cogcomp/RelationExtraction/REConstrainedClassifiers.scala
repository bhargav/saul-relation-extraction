/** This software is released under the University of Illinois/Research and Academic Use License. See
  * the LICENSE file in the root folder for details. Copyright (c) 2016
  *
  * Developed by: The Cognitive Computations Group, University of Illinois at Urbana-Champaign
  * http://cogcomp.cs.illinois.edu/
  */
package edu.illinois.cs.cogcomp.RelationExtraction

import edu.illinois.cs.cogcomp.illinoisRE.data.SemanticRelation
import edu.illinois.cs.cogcomp.saul.classifier.ConstrainedClassifier
import edu.illinois.cs.cogcomp.RelationExtraction.REClassifiers._
import edu.illinois.cs.cogcomp.infer.ilp.OJalgoHook

object REConstrainedClassifiers {

  object relationHierarchyConstrainedClassifier extends ConstrainedClassifier[SemanticRelation, SemanticRelation](
    relationTypeFineClassifier
  ) {
    def subjectTo = REConstraints.relationHierarchyConstraint
    override def solver = new OJalgoHook
  }
}
