/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.studyanalysis;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author louismccallum
 */
public class Study2LogsExperiment extends LogsExperiment {
    public final String LOG_NAME = "featurnator_study_2";
    private final String PROJECT_NAME = "Week6";
    private final String ROOT_DIR = "/Users/louismccallum/Documents/Goldsmiths/Study_2_logs/projects";
    private final String RESULTS_DIR = "/Users/louismccallum/Documents/Goldsmiths/Study_2_analysis";
    private final String[] blackList = new String[] {};
    
    public static void main(String[] args)
    {
        Study2LogsExperiment e = new Study2LogsExperiment();
        e.runTests();
    }
    
    @Override
    public String getRootDir()
    {
        return ROOT_DIR;
    }
    
    @Override
    public String getProjectName()
    {
        return PROJECT_NAME;
    }
    
    @Override
    public String getResultsDir()
    {
        return RESULTS_DIR;
    }
    
    @Override
    public String[] getBlacklist()
    {
        return blackList;
    }
    
    @Override
    public void runForNextParticipant()
    {
        reset();
                
        if(participantIterator.hasNext())
        {
            Map.Entry pair = (Map.Entry)participantIterator.next();
       
            while(isBlackListed((String)pair.getKey()))
            {
                System.out.println("Skipping " + (String)pair.getKey() + "(blacklisted)");
                if(!participantIterator.hasNext())
                {
                    logAll();
                    return;
                }
                pair = (Map.Entry)participantIterator.next();
            }

            System.out.println(pair.getKey() + " = " + pair.getValue());
            String location = (String) pair.getValue();
            String pID = (String) pair.getKey();
            ArrayList<String> lines = new ArrayList();
            participant.participantID = pID;
            try 
            {
                Files.lines(Paths.get(location)).forEach(line->lines.add(line));
            } 
            catch (Exception e)
            {
                
            }
            long cumSumRunning = 0;
            long cumSumRecording = 0;
            long prevStart = 0;
            int cvCtr = 0;
            int runCtr = 0;
            int auto = 0;
            int remove = 0;
            int add = 0;
            int threshold = 0;
            int newFeatureRunCtr = 0;
            int newDataRunCtr = 0;
            int newFeatureCVCtr = 0;
            int newDataCVCtr = 0;
            int newFeatureRecCtr = 0;
            int newDataRecCtr = 0;
            int newFeatureFeatureCtr = 0;
            int newDataFeatureCtr = 0;
            int manualEdits = 0;
            boolean running = false;
            boolean recording = false;
            boolean didChangeFeatures = false;
            boolean didChangeData = false;
            ArrayList<String> exploredFeatures = new ArrayList();
            
            for(String line : lines)
            {
                boolean featuresAdded = false;
                String split[] = line.split(",");
                if(split[2].equals("EVAL_ALL_FEATURES"))
                {
                    participant.didEvalLargeLast = true;
                    participant.didEvalRawLast = false;
                }
                if(split[2].equals("EVAL_RAW_FEATURES"))
                {
                    participant.didEvalLargeLast = false;
                    participant.didEvalRawLast = true;
                }
                if(split[2].equals("AUTO_SELECT"))
                {
                    auto++;
                    
                    newDataFeatureCtr += didChangeData ? 1 : 0;
                    didChangeData = false;
                    
                    didChangeFeatures = true;
                    featuresAdded = true;
                }
                if(split[2].equals("THRESHOLD_SELECT"))
                {
                    threshold++;
                    
                    newDataFeatureCtr += didChangeData ? 1 : 0;
                    didChangeData = false;
                    
                    didChangeFeatures = true;
                    featuresAdded = true;
                }
                if(split[2].equals("REMOVE_PANEL"))
                {
                    remove++;
                }
                if(split[2].equals("ADD_PANEL"))
                {
                    add++;
                }
                if(split[2].equals("START_RUN"))
                {
                    prevStart = Long.parseUnsignedLong(split[0]);
                    running = true;
                }
                if(running && split[2].equals("RUN_STOP"))
                {
                    long endTime = Long.parseUnsignedLong(split[0]);
                    cumSumRunning += (endTime - prevStart);
                    runCtr++;
                    running = false;
                    newFeatureRunCtr += didChangeFeatures ? 1 : 0;
                    newDataRunCtr += didChangeData ? 1 : 0;
                    didChangeFeatures = false;
                    didChangeData = false;
                }
                if(split[2].equals("SUPERVISED_RECORD_START"))
                {
                    prevStart = Long.parseUnsignedLong(split[0]);
                    recording = true;
                    
                    newFeatureRecCtr += didChangeFeatures ? 1 : 0;
                    didChangeFeatures = false;
                    
                    didChangeData = true;
                }
                if(recording && split[2].equals("SUPERVISED_RECORD_STOP"))
                {
                    long endTime = Long.parseUnsignedLong(split[0]);
                    cumSumRecording += (endTime - prevStart);
                    recording = false;
                }
                if(line.contains("CROSS_VALIDATATION"))
                {
                    cvCtr++;
                    newFeatureCVCtr += didChangeFeatures ? 1 : 0;
                    newDataCVCtr += didChangeData ? 1 : 0;
                    didChangeFeatures = false;
                    didChangeData = false;
                }
                if(split[2].equals("FEATURES_REMOVED") || split[2].equals("FEATURES_ADDED"))
                {
                    
                    newDataFeatureCtr += didChangeData ? 1 : 0;
                    didChangeData = false;
                    
                    didChangeFeatures = true;
                    manualEdits++;
                    if(split[2].equals("FEATURES_ADDED"))
                    {
                        featuresAdded = true;
                    }
                }
                if(featuresAdded)
                {
                    if(split[3].length() > 3)
                    {
                        for(int i = 3; i < split.length; i++)
                        {
                            String ft = split[i];
                            if(i == 3)
                            {
                                ft = ft.substring(1);
                            }
                            if(i == split.length - 1)
                            {
                                ft = ft.substring(0, ft.length() - 1);
                            }
                            if(!exploredFeatures.contains(ft))
                            {
                                exploredFeatures.add(ft);
                            }
                        } 
                    }
                }
            }
            
            String[] f = new String[exploredFeatures.size()];
            f = exploredFeatures.toArray(f);
            participant.addPanelCount = add;
            participant.manualEditCount = manualEdits;
            participant.removePanelCount = remove;
            participant.thresholdSelectCount = threshold;
            participant.features.put("explored", f);
            participant.timeSpentRunning = cumSumRunning;
            participant.timeSpentRecording = cumSumRecording;
            participant.cvCount = cvCtr;
            participant.runCount = runCtr;
            participant.newDataRunCount = newDataRunCtr;
            participant.newFeatureRunCount = newFeatureRunCtr;
            participant.newDataRecCount  = newDataRecCtr;
            participant.newFeatureRecCount = newFeatureRecCtr;
            participant.newDataCVCount  = newDataCVCtr;
            participant.newFeatureCVCount = newFeatureCVCtr;
            participant.newDataFeatureCount  = newDataFeatureCtr;
            participant.newFeatureFeatureCount = newFeatureFeatureCtr;
            participant.autoSelectCount = auto;
            participantIterator.remove(); 
            logParticipant();
            runForNextParticipant();
        }
        else
        {
            logAll();
            return;
        }
    }
    
    @Override
    public HashMap<String, String> getProjectLocations()
    {
        HashMap<String, String> projects = new HashMap();
        File folder = new File(getRootDir());
        File[] listOfFiles = folder.listFiles();
        for(File idFile : listOfFiles)
        {
            if(idFile.isDirectory())
            {
                String pID = idFile.getName();
                for(File projectFile : idFile.listFiles())
                {
                    if(projectFile.isDirectory() && projectFile.getName().contains(getProjectName()))
                    {
                        File[] listOfStudyFiles = projectFile.listFiles();
                        for(File studyFile : listOfStudyFiles)
                        {
                            if(studyFile.getName().contains(LOG_NAME))
                            {
                                File[] listOfLogFiles = studyFile.listFiles();
                                for(File logFile : listOfLogFiles)
                                {
                                    if(logFile.getName().contains(LOG_NAME))
                                    {
                                        System.out.println(logFile.getName());
                                        projects.put(pID, logFile.getAbsolutePath());
                                        break;
                                    }
                                }
                            } 
                        }
                    }
                }
            }
        }
        return projects;
    }
}
