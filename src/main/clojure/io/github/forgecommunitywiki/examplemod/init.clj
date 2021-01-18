(ns io.github.forgecommunitywiki.examplemod.init
  (:import (net.minecraftforge.fml.common Mod)))

(gen-class :name ^{Mod "examplemod"}
           io.github.forgecommunitywiki.examplemod.ExampleMod
           :main false)