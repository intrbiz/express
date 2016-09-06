package com.intrbiz.express.template.loader;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import com.intrbiz.express.template.filter.HTMLContentFilter;
import com.intrbiz.express.template.filter.PlainTextContentFilter;
import com.intrbiz.express.template.filter.XMLContentFilter;

public class NameSuffixRegistry implements Iterable<NameSuffix>
{
    private CopyOnWriteArrayList<NameSuffix> registry = new CopyOnWriteArrayList<NameSuffix>();

    public NameSuffixRegistry()
    {
        super();
    }

    @Override
    public Iterator<NameSuffix> iterator()
    {
        return this.registry.iterator();
    }

    @Override
    public void forEach(Consumer<? super NameSuffix> action)
    {
        this.registry.forEach(action);
    }

    @Override
    public Spliterator<NameSuffix> spliterator()
    {
        return this.registry.spliterator();
    }

    public List<NameSuffix> get()
    {
        return this.registry;
    }

    public void add(NameSuffix suffix)
    {
        this.registry.add(suffix);
    }

    public static NameSuffixRegistry getDefault()
    {
        NameSuffixRegistry reg = new NameSuffixRegistry();
        reg.add(new NameSuffix("etp", new PlainTextContentFilter()));
        reg.add(new NameSuffix("eth", new HTMLContentFilter()));
        reg.add(new NameSuffix("etx", new XMLContentFilter()));
        return reg;
    }
}
