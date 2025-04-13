package de.kallecrafter.globalrangs.Listener;

import de.kallecrafter.globalrangs.GlobalRangsMain;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.playerlist.PlayerListUpdateEvent;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;

public class NameTagListener {


  private final GlobalRangsMain addon;

  public NameTagListener(GlobalRangsMain addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onNameTagReceive(PlayerNameTagRenderEvent event) {
    String playerRank = getPlayerrank(event.getPlayerInfo().getTeam().getSuffix());
    Component nameTag = event.getPlayerInfo().getTeam().getPrefix();
    Component icon = null;
    if (playerRank != null) {
      switch (playerRank) {
        case "Owner":
          if (!Laby.references().serverController().getCurrentStorageServerData().getName().toString().toLowerCase().contains("craftergang")) {
            icon = Component.icon(Icon.texture(ResourceLocation.create("globalrangs", "textures/rangs/ownerred.png"))).setHeight(12).setWidth(22);
          } else {
            icon = Component.icon(Icon.texture(ResourceLocation.create("globalrangs", "textures/rangs/ownerblue.png"))).setHeight(12).setWidth(22);
          }
          for (int i = 0; i < nameTag.getChildren().size(); i++) {
            Component c = nameTag.getChildren().get(i);
            TextComponent t = ((TextComponent) c);
            boolean space = t.getText().endsWith(" ");
            if (!t.getText().startsWith(playerRank))
              continue;
            Component n = icon.append(Component.text(space ? " " : ""));
            nameTag.replace(i, n);
            nameTag = nameTag.append(Component.text(" ").append(nameTag));
            break;
          }
          break;
        case "Admin":
          icon = Component.icon(Icon.texture(ResourceLocation.create("globalrangs", "textures/rangs/admin.png"))).setHeight(12).setWidth(22);
          break;
        case "Mod":
          icon = Component.icon(Icon.texture(ResourceLocation.create("globalrangs", "textures/rangs/mod.png"))).setHeight(12).setWidth(22);
          break;
        case "VIP":
          icon =Component.icon(Icon.texture(ResourceLocation.create("globalrangs", "textures/rangs/vip.png"))).setHeight(12).setWidth(22);
          break;
      }
    }
    Component playerName = Component.text(event.getPlayerInfo().profile().getUsername());
    Component finalTag = Component.empty().append(icon).append(Component.text(" ")).append(playerName);
    event.setNameTag(finalTag);
  }



  public static String getPlayerrank(Component rang) {
    String rangName = null;
    if (rang != null) {
      rangName = rang.toString();
      if (rangName.contains("Owner")) {
        int index = rangName.indexOf("Owner");

        if (index != -1) {
          String foundWord = rangName.substring(index, index + "Owner".length());
          return foundWord;
        }
      } else if (rangName.contains("Admin")) {
        int index = rangName.indexOf("Admin");

        if (index != -1) {
          String foundWord = rangName.substring(index, index + "Admin".length());
          return foundWord;
        }
      }
    }
    return null;
  }

}
