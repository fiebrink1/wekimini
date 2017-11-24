/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import weka.core.Instances;
import wekimini.featureanalysis.WrapperSelector;
import wekimini.featureanalysis.InfoGainSelector;
import wekimini.learning.SupervisedLearningModel;
import weka.classifiers.Classifier;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;

/**
 *
 * @author louismccallum
 */
public class FeatureSelectorTest {
    
    public Wekinator w;
            
    @Before
    public void setUp() {
        String fileLocation = getTestSetPath();
        try{
            w = WekinatorSaver.loadWekinatorFromFile(fileLocation);
        } catch (Exception e)
        {
            
        }
    }
    
    public String getTestSetPath()
    {
       return "/Users/louismccallum/Documents/Goldsmiths/Wekinator_Projects/Small6-1/Small6-1/Small6-1.wekproj";
    }
    
    @Test 
    @Ignore public void testUpdateAllFeatures()
    {
        Method method;
        try {
            method = w.getDataManager().getClass().getDeclaredMethod("updateAllFeaturesInstances");
            method.setAccessible(true);
            method.invoke(w.getDataManager());
            int attributes = w.getDataManager().getAllFeaturesInstances().numAttributes();
            int allFeaturesOutputSize = w.getDataManager().featureManager.getAllFeaturesGroup().getOutputDimensionality();
            assertEquals(allFeaturesOutputSize, attributes - 1, 0);
            //assertEquals(w.getDataManager().featureManager.getAllFeaturesGroup().valueMap)
        } catch (Exception e) {
            
        }
    }
    
    @Test
    @Ignore public void testAutomaticSelect() throws InterruptedException
    {
        w.getSupervisedLearningManager().setLearningState(SupervisedLearningManager.LearningState.READY_TO_TRAIN);
        w.getSupervisedLearningManager().setRunningState(SupervisedLearningManager.RunningState.NOT_RUNNING);
        w.getSupervisedLearningManager().buildAll();
        Thread.sleep(50);
        w.getDataManager().selectFeaturesAutomatically();
        int attributes = w.getDataManager().getAllFeaturesInstances().numAttributes();
        int allFeaturesOutputSize = w.getDataManager().featureManager.getAllFeaturesGroup().getOutputDimensionality();
        assertEquals(allFeaturesOutputSize, attributes - 1, 0);
        List<Instances> featureInstances = w.getDataManager().getFeatureInstances();
    }
    
    @Test
    @Ignore public void testWrapperSelection() throws InterruptedException
    {
        w.getSupervisedLearningManager().setLearningState(SupervisedLearningManager.LearningState.READY_TO_TRAIN);
        w.getSupervisedLearningManager().setRunningState(SupervisedLearningManager.RunningState.NOT_RUNNING);
        w.getSupervisedLearningManager().buildAll();
        Thread.sleep(50);
        List<Instances> featureInstances = w.getDataManager().getFeatureInstances();
        WrapperSelector wrapperSelector = new WrapperSelector();
        int ptr = 0;
        for(Instances data:featureInstances)
        {
            Path path = w.getSupervisedLearningManager().getPaths().get(ptr);
            SupervisedLearningModel model = (SupervisedLearningModel)path.getModel();
            Classifier c = model.getClassifier();
            wrapperSelector.classifier = c;
            int[] indexes = wrapperSelector.getAttributeIndicesForInstances(data);
            System.out.println("completed model check:" + ptr);
            ptr++;
        }

        System.out.println("done");
    }
    
    @Test
    public void testInfoGainSelection() throws InterruptedException
    {
        w.getSupervisedLearningManager().setLearningState(SupervisedLearningManager.LearningState.READY_TO_TRAIN);
        w.getSupervisedLearningManager().setRunningState(SupervisedLearningManager.RunningState.NOT_RUNNING);
        w.getSupervisedLearningManager().buildAll();
        Thread.sleep(50);
        List<Instances> featureInstances = w.getDataManager().getFeatureInstances();
        InfoGainSelector sel = new InfoGainSelector();
        int ptr = 0;
        for(Instances data:featureInstances)
        {
            int[] indexes = sel.getAttributeIndicesForInstances(data);
            System.out.println("completed model check:" + ptr);
            ptr++;
        }
        Method method;
        try {
            method = w.getDataManager().getClass().getDeclaredMethod("updateAllFeaturesInstances");
            method.setAccessible(true);
            method.invoke(w.getDataManager());
        } catch (Exception ex) {
            Logger.getLogger(FeatureSelectorTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Instances allFeatures = w.getDataManager().getAllFeaturesInstances();
        int[] indexes = sel.getAttributeIndicesForInstances(allFeatures);
        System.out.println("done");
    }
    
}
