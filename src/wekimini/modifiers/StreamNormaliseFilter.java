/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.modifiers;
import weka.core.*;
import weka.filters.*;
/**
 *
 * @author louismccallum
 */
public class StreamNormaliseFilter extends SimpleStreamFilter {
    
    public BatchNormaliseFilter batchFilter;
    
    @Override
    public String globalInfo() {
        return "A filter to normalise an Instance, attribute by attribute, given a"
                + "completed batch filter";
    }

    @Override
    protected Instances determineOutputFormat(Instances i){
        return i;
    }

    @Override
    protected Instance process(Instance instance) throws NullPointerException {
        double[] newVals = new double[instance.numAttributes()];
        if(!batchFilter.isValid(instance))
        {
            return instance;
        }
        for(int j = 0; j < instance.numAttributes(); j++)
        {
            Attribute a = instance.attribute(j);
            double val = instance.value(j);
            double minVal = batchFilter.minForAttribute(a);
            double maxVal = batchFilter.maxForAttribute(a);
            double delta =  maxVal - minVal;
            if(delta == 0)
            {
                delta = 1.0;
            }
            if(j != instance.classIndex())
            {
                //newVals[j] = ((val - minVal) / delta) * 10000000;
                newVals[j] = ((val - minVal) / delta);
            }
            else
            {
                newVals[j] = val;
            }
        }
        return new Instance(1, newVals);
    }
}
