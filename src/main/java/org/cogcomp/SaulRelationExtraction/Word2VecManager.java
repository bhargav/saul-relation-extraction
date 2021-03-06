/** This software is released under the University of Illinois/Research and Academic Use License. See
  * the LICENSE file in the root folder for details. Copyright (c) 2016
  *
  * Developed by: The Cognitive Computations Group, University of Illinois at Urbana-Champaign
  * http://cogcomp.cs.illinois.edu/
  */
package org.cogcomp.SaulRelationExtraction;

import edu.illinois.cs.cogcomp.core.io.LineIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Loads and Serves Word2Vector Vectors trained on ACE2005 dataset and Wikipedia data.
 * Adopted from Zefu Lu's codebase at: https://gitlab-beta.engr.illinois.edu/cogcomp/illinois_RE_SL
 */
public final class Word2VecManager {
    private String UNK = "UNK";
    private Map<String, double[]> map = new HashMap<>();
    private int vector_size;

    private static Logger logger = LoggerFactory.getLogger(Word2VecManager.class);

    public Word2VecManager(String vectorFilePath) {
        List<String> lines = new ArrayList<>();

        try {
            lines.addAll(LineIO.read(vectorFilePath));
        } catch (FileNotFoundException ex) {
            logger.error("Word2Vec Cluster path not found at " + vectorFilePath, ex);
        }

        boolean firstLine = true;
        this.vector_size = 0;
        for (String s : lines) {
            if (firstLine) {
                firstLine = false;
                String[] list = s.split(" ");
                vector_size = Integer.parseInt(list[1]);
                logger.info("vector_size:" + vector_size);
                continue;
            }
            String[] list = s.split(" ");
            String key = list[0];
            double[] vector = new double[vector_size];
            for (int i = 0; i < vector_size; i++) {
                vector[i] = Double.parseDouble(list[i + 1]);
            }
            map.put(key, vector);
        }

        logger.info("Word2Vec Manager Loaded");
    }

    public double[] getWordVector(String key) {
        if (key == null) {
            return new double[this.vector_size];
        }
        if (key.equals("'s")) {
            key = "has";
        }
        if ((key.endsWith(".") || key.endsWith(",")) && key.length() > 1 && !key.equals("...")) {
            String new_key = key.substring(0, key.length() - 2);
            if (map.containsKey(new_key))
                return map.get(new_key);
        }

        if (map.containsKey(key)) {
            return map.get(key);
        }
        return new double[this.vector_size];
    }

    public static void main(String[] argv) {
        String path = argv.length > 0 ? argv[0] : "";
        Word2VecManager manager = new Word2VecManager(path);
        logger.info(Arrays.toString(manager.getWordVector("this")));
    }
}
