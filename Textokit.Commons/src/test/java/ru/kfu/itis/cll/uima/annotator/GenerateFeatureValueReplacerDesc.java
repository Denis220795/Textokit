/**
 *
 */
package ru.kfu.itis.cll.uima.annotator;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Rinat Gareev
 */
public class GenerateFeatureValueReplacerDesc {

    /**
     * @param args
     * @throws UIMAException
     * @throws IOException
     * @throws SAXException
     */
    public static void main(String[] args) throws UIMAException, SAXException, IOException {
        AnalysisEngineDescription desc = AnalysisEngineFactory
                .createEngineDescription(FeatureValueReplacer.class);
        desc.toXML(new FileOutputStream(
                "src/main/resources/ru/kfu/itis/cll/uima/annotator/FeatureValueReplacer.xml"));
    }

}