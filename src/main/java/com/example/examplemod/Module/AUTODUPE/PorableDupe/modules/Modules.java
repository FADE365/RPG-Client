package com.example.examplemod.Module.AUTODUPE.PorableDupe.modules;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* All Modules record */

@SideOnly(Side.CLIENT)
public class Modules {

  private final List<Module> modules;

  private final Configuration configuration;

  public Modules(File configuration_file) {

    this.configuration = new Configuration(configuration_file);

    this.modules = new ArrayList<Module>();
    this.modules.add(new CommandsModule());
    this.modules.add(new PacketInterceptionModule());
    this.load();
  }

  public List<Module> getModules() {
    return this.modules;
  }

  public void onDisconnect() {
    for(Module i : this.modules) {
      i.onDisconnect();
    }
  }


  public Configuration getConfiguration() {
    return this.configuration;
  }

  public Module getByClass(Class<?> clazz) {
    for (Module m : this.modules) {
      if (m.getClass() == clazz) return m;
    }
    return null;
  }
  public Module getByName(String name) {
    for(Module m : this.modules) {
      if(m.getLabel().equals(name)) return m;
    }
    return null;
  }

  private void load() {
    for(Module i : this.modules) {
      i.load(this.configuration);
    }
  }

  public void save() {
    for(Module i : this.modules) {
      i.save(this.configuration);
    }
    this.configuration.save();
  }

}
