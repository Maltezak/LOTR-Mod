package lotr.client.sound;

import java.io.*;
import java.util.*;

import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;

public class LOTRMusicResourceManager implements IResourceManager {
    private Map<ResourceLocation, IResource> resourceMap = new HashMap<>();

    @Override
    public Set getResourceDomains() {
        return this.resourceMap.entrySet();
    }

    @Override
    public IResource getResource(ResourceLocation resource) throws IOException {
        return this.resourceMap.get(resource);
    }

    @Override
    public List getAllResources(ResourceLocation resource) throws IOException {
        ArrayList<IResource> list = new ArrayList<>();
        list.add(this.getResource(resource));
        return list;
    }

    public void registerMusicResources(ResourceLocation resource, InputStream in) {
        SimpleResource ires = new SimpleResource(resource, in, null, null);
        this.resourceMap.put(resource, ires);
    }
}
