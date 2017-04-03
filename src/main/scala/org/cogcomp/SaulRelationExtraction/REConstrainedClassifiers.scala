/** This software is released under the University of Illinois/Research and Academic Use License. See
  * the LICENSE file in the root folder for details. Copyright (c) 2016
  *
  * Developed by: The Cognitive Computations Group, University of Illinois at Urbana-Champaign
  * http://cogcomp.cs.illinois.edu/
  */
package org.cogcomp.SaulRelationExtraction

import edu.illinois.cs.cogcomp.illinoisRE.data.SemanticRelation
import edu.illinois.cs.cogcomp.infer.ilp.OJalgoHook
import edu.illinois.cs.cogcomp.saul.classifier.ConstrainedClassifier
import org.cogcomp.SaulRelationExtraction.REClassifiers._

object REConstrainedClassifiers {

  object relationHierarchyConstrainedClassifier extends ConstrainedClassifier[SemanticRelation, SemanticRelation](
    relationTypeFineClassifier
  ) {
    def subjectTo = REConstraints.relationHierarchyConstraint
    override def solver = new OJalgoHook
  }
}
