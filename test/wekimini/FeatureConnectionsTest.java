/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import wekimini.modifiers.ModifiedInput;
import static org.junit.Assert.*;
/**
 *
 * @author louismccallum
 */
public class FeatureConnectionsTest {
    
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
       return "/Users/louismccallum/Documents/Goldsmiths/Wekinator_Projects/WekinatorTestSet/WekinatorTestSet/WekinatorTestSet.wekproj";
    }
    
    @Test
    public void testGetConnections()
    {
        boolean[][] connections = w.getLearningManager().getConnectionMatrix(true);
        assertEquals(w.getDataManager().featureManager.getFeatureNames().length, connections.length);
        assertEquals(w.getDataManager().featureManager.getFeatureGroups().size(), connections[0].length);
        
        for(int i = 0; i < connections.length; i++)
        {
            for(int j = 0; j < connections[i].length; j++)
            {
                assertEquals(false,connections[i][j]);
            }
        }
        
        w.getDataManager().featureManager.getFeatureGroups().get(0).addFeatureForKey("MaxBinFFTAccX");
        connections = w.getLearningManager().getConnectionMatrix(true);
        
        for(int i = 0; i < connections.length; i++)
        {
            for(int j = 0; j < connections[i].length; j++)
            {
                assertEquals((i == 164 && j == 0), connections[i][j]);
            }
        }
    }
    
    @Test 
    public void testSetConnections()
    {
        boolean[][] connections = w.getLearningManager().getConnectionMatrix(true);
        int numInputs = w.getDataManager().featureManager.getFeatureNames().length;
        int numOutputs = w.getDataManager().featureManager.getFeatureGroups().size();
        assertEquals(numInputs, connections.length);
        assertEquals(numOutputs, connections[0].length);
        
        for(int i = 0; i < connections.length; i++)
        {
            for(int j = 0; j < connections[i].length; j++)
            {
                assertEquals(false,connections[i][j]);
            }
        }
        
        boolean[][] newConnections = new boolean[numInputs][numOutputs];
        newConnections[0][0] = true;
        newConnections[2][2] = true;
        
        w.getLearningManager().updateInputOutputConnections(newConnections, true);
        
        connections = w.getLearningManager().getConnectionMatrix(true);
        
        for(int i = 0; i < connections.length; i++)
        {
            for(int j = 0; j < connections[i].length; j++)
            {
                assertEquals((i == 0 && j == 0) || (i == 2 && j == 2), connections[i][j]);
            }
        }
        
        //AccX
        List<ModifiedInput> modifiers1 = w.getDataManager().featureManager.featureCollections.get(0).getModifiers().getModifiers();
        assertEquals(2, modifiers1.size(), 0);
        //Jsut raw
        List<ModifiedInput> modifiers2 = w.getDataManager().featureManager.featureCollections.get(1).getModifiers().getModifiers();
        assertEquals(1, modifiers2.size(), 0);
        //AccZ
        List<ModifiedInput> modifiers3 = w.getDataManager().featureManager.featureCollections.get(2).getModifiers().getModifiers();
        assertEquals(2, modifiers3.size(), 0);
    }
}
