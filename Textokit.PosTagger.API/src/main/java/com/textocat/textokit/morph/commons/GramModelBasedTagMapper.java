/**
 *
 */
package com.textocat.textokit.morph.commons;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.GramModelHolder;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionaryHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.fit.component.initialize.ExternalResourceInitializer;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.fit.factory.initializable.Initializable;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import java.util.BitSet;
import java.util.Set;

import static com.textocat.textokit.morph.dictionary.resource.MorphDictionaryUtils.toGramBits;

/**
 * @author Rinat Gareev
 */
public class GramModelBasedTagMapper implements TagMapper, Initializable {

    /**
     * Add dependency on an external resource with {@link MorphDictionaryHolder}
     * API
     *
     * @param clientResourceDesc description of a resource (e.g., annotator) that initialize
     *                           this tag mapper.
     * @throws ResourceInitializationException
     */
    public static void declareResourceDependencies(ResourceSpecifier clientResourceDesc)
            throws ResourceInitializationException {
        ExternalResourceFactory.createDependency(clientResourceDesc,
                GramModelBasedTagMapper.RESOURCE_GRAM_MODEL,
                GramModelHolder.class);
    }

	/*
    ExternalResourceFactory.bindResource(clientResourceDesc,
			GramModelBasedTagMapper.RESOURCE_GRAM_MODEL, gramModelDesc);
			*/

    public static final String RESOURCE_GRAM_MODEL = "gramModel";
    // config fields
    @ExternalResource(key = RESOURCE_GRAM_MODEL, mandatory = true)
    private GramModelHolder gramModelHolder;
    // derived
    private GramModel gramModel;

    // for UIMA
    public GramModelBasedTagMapper() {
    }

    // for stand-alone usage
    public GramModelBasedTagMapper(GramModel gramModel) {
        this.gramModel = gramModel;
    }

    @Override
    public void initialize(UimaContext ctx) throws ResourceInitializationException {
        ExternalResourceInitializer.initialize(this, ctx);
        gramModel = gramModelHolder.getGramModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> parseTag(String tag, String token) {
        return parseTag(tag);
    }

    public static Set<String> parseTag(String tag) {
        if (StringUtils.isEmpty(tag) || tag.equalsIgnoreCase("null")) {
            return Sets.newLinkedHashSet();
        }
        return Sets.newLinkedHashSet(targetGramSplitter.split(tag));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toTag(Set<String> grams) {
        BitSet wfBits = toGramBits(gramModel, grams);
        return toTag(wfBits);
    }

    public String toTag(BitSet wfBits) {
        if (wfBits.isEmpty()) {
            return null;
        }
        return targetGramJoiner.join(gramModel.toGramSet(wfBits));
    }

    public static final String targetGramDelim = "&";
    public static final Joiner targetGramJoiner = Joiner.on(targetGramDelim);
    public static final Splitter targetGramSplitter = Splitter.on(targetGramDelim);
}
