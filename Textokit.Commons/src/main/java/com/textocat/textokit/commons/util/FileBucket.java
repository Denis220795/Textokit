/**
 *
 */
package com.textocat.textokit.commons.util;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.Collections;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
class FileBucket implements Comparable<FileBucket> {
    private Set<File> files = Sets.newLinkedHashSet();
    // state fields
    private Long size = 0L;

    FileBucket() {
    }

    @Override
    public int compareTo(FileBucket arg) {
        return size.compareTo(arg.size);
    }

    public void add(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException(String.format(
                    "%s is not an existing file", file));
        }
        if (files.add(file)) {
            size += file.length();
        }
    }

    public Set<File> getFiles() {
        return Collections.unmodifiableSet(files);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("filesNum", files.size())
                .add("size", size)
                .toString();
    }
}
