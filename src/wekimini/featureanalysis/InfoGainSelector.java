/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.featureanalysis;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.Discretize;

/**
 *
 * @author louismccallum
 */
public class InfoGainSelector extends FeatureSelector {
 
    double threshold = 0.2;
    
    @Override
    public int[] getAttributeIndicesForInstances(Instances instances)
    {
        try {

            int classIndex = instances.classAttribute().index();
            int [] toRemove = {classIndex};
            
            Remove removeClass = new Remove();   
            removeClass.setAttributeIndicesArray(toRemove);
            removeClass.setInputFormat(instances);                          
            Instances noClass = Filter.useFilter(instances, removeClass);  

            Discretize dis = new Discretize();
            dis.setInputFormat(noClass);     
            Instances discreted = Filter.useFilter(noClass, dis); 
            
            AttributeSelection attsel = new AttributeSelection();
            InfoGainAttributeEval eval = new InfoGainAttributeEval();
            Ranker search = new Ranker();
            search.setOptions(new String[]{"-T","0.05"});
            attsel.setEvaluator(eval);
            attsel.setSearch(search);
            instances.setClassIndex(instances.numAttributes() - 1);
            
            System.out.println("starting selection");
            attsel.SelectAttributes(discreted);
            System.out.println("DONE");  
            
            //Return best results from ranked array
            int[] ranked =  attsel.selectedAttributes();
            int i = (int)(((double)ranked.length)*threshold);
            int[] thresholded = new int[i];
            System.arraycopy(ranked, 0, thresholded, 0, i);
            return thresholded;
            
        } catch (Exception ex) {
            Logger.getLogger(InfoGainSelector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new int[0];
    }
    
}
