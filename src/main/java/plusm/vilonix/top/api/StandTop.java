package plusm.vilonix.top.api;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import plusm.vilonix.api.VilonixNetwork;
import plusm.vilonix.api.depend.CraftVector;
import plusm.vilonix.api.entity.EntityAPI;
import plusm.vilonix.api.entity.EntityEquip;
import plusm.vilonix.api.entity.stand.CustomStand;
import plusm.vilonix.api.hologram.Hologram;
import plusm.vilonix.api.hologram.HologramAPI;
import plusm.vilonix.api.hologram.lines.TextHoloLine;
import plusm.vilonix.api.player.BukkitGamer;
import plusm.vilonix.api.util.Head;
import plusm.vilonix.api.util.ItemUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StandTop {
    private static final HologramAPI HOLOGRAM_API = VilonixNetwork.getHologramAPI();
    private static final EntityAPI ENTITY_API = VilonixNetwork.getEntityAPI();
    private final int position;
    private final Top topType;
    private final CustomStand customStand;
    private final Map<Integer, Hologram> holograms = new ConcurrentHashMap<>();
    private final Location location;
    private StandTopData standTopData;

    public StandTop(final Top topType, final Location location, final int position) {
        this.position = position;
        String posColor;
        if (position == 1)
            posColor = "§6§l";
        else if (position == 2)
            posColor = "§a";
        else if (position == 3)
            posColor = "§b";
        else if (position == 4)
            posColor = "§7";
        else
            posColor = "§4";
        this.topType = topType;
        this.location = location;
        this.customStand = StandTop.ENTITY_API.createStand(location);
        this.customStand.setArms(true);
        this.customStand.setSmall(true);
        this.customStand.setBasePlate(false);
        this.setEquipment();

        final Hologram hologram = StandTop.HOLOGRAM_API.createHologram(location.clone().add(0.0, 1.2, 0.0));
        hologram.addTextLine(posColor + position + "§f место");
        hologram.addTextLine("§cникто не занял это");
        hologram.addTextLine("§cместо, будь первым!");
        this.holograms.put(0, hologram);

    }

    public void setStandTopData(final StandTopData standTopData) {
        this.standTopData = standTopData;
        this.update();
    }

    public void update() {
        if (this.standTopData == null) {
            return;
        }
        for (final Map.Entry<Integer, Hologram> entry : this.holograms.entrySet()) {
            final Hologram hologram = entry.getValue();
            final TextHoloLine holoLine1 = hologram.getHoloLine(1);
            final TextHoloLine holoLine2 = hologram.getHoloLine(2);
            holoLine1.setText(this.standTopData.getDisplayName());
            holoLine2.setText(this.standTopData.getLastString());
        }
        this.customStand.getEntityEquip().setHelmet(this.standTopData.getHead());
    }

    public Location getLocation() {
        return this.location.clone();
    }

    private void setEquipment() {
        Color color;
        ItemStack itemInHand;
        switch (this.position) {
            case 1: {
                color = Color.fromRGB(238, 201, 0);
                itemInHand = ItemUtil.getBuilder(Material.GOLDEN_APPLE).glowing().build();
                /*
                /summon armor_stand ~ ~ ~ {NoBasePlate:1b,ShowArms:1b,Small:1b,Pose:{Body:[343f,0f,0f],
                Head:[337f,12f,8f],LeftLeg:[0f,343f,337f],RightLeg:[0f,30f,8f],LeftArm:[313f,319f,2f],RightArm:[247f,46f,360f]}}
                 */
                this.customStand.setBodyPose(new CraftVector(343f,0f,0f));
                this.customStand.setLeftArmPose(new CraftVector(313f,327f,0f));
                this.customStand.setRightArmPose(new CraftVector(247f,46f,360f));
                this.customStand.setRightLegPose(new CraftVector(0f,30f,8f));
                this.customStand.setLeftLegPose(new CraftVector(0f,343f,337f));
                this.customStand.setHeadPose(new CraftVector(337f,12f,8f));
                break;
            }
            case 2: {
                color = Color.fromRGB(58, 225, 0);
                itemInHand = new ItemStack(Material.EMERALD);
                /*
                /summon armor_stand ~ ~ ~ {NoBasePlate:1b,ShowArms:1b,Small:1b,Pose:{Body:[8f,0f,0f],Head:[327f,0f,0f],
                LeftLeg:[0f,337f,346f],RightLeg:[337f,24f,0f],LeftArm:[291f,26f,0f],RightArm:[289f,339f,0f]}}
                 */
                this.customStand.setBodyPose(new CraftVector(8f,0f,0f));
                this.customStand.setHeadPose(new CraftVector(327f,0f,0f));
                this.customStand.setLeftLegPose(new CraftVector(0f,337f,346f));
                this.customStand.setRightLegPose(new CraftVector(337f,24f,0f));
                this.customStand.setLeftArmPose(new CraftVector(291f,26f,0f));
                this.customStand.setRightArmPose(new CraftVector(289f,339f,0f));

                break;
            }
            case 3: {
                color = Color.fromRGB(58, 224, 254);
                itemInHand = new ItemStack(Material.DIAMOND);
                /*
                /summon armor_stand ~ ~ ~ {NoBasePlate:1b,ShowArms:1b,Small:1b,Rotation:[90f],Pose:{Head:[0f,271f,352f],
                LeftLeg:[269f,0f,18f],RightLeg:[16f,24f,0f],LeftArm:[54f,0f,0f],RightArm:[309f,0f,4f]}}
                 */
                this.customStand.setLook(44,7);
                //this.customStand.setBodyPose(new CraftVector());
                this.customStand.setHeadPose(new CraftVector(0f,271f,352f));
                this.customStand.setLeftLegPose(new CraftVector(269f,0f,18f));
                this.customStand.setRightLegPose(new CraftVector(16f,24f,0f));
                this.customStand.setLeftArmPose(new CraftVector(54f,0f,0f));
                this.customStand.setRightArmPose(new CraftVector(309f,0f,4f));
                break;
            }
            case 4: {
                color = Color.fromRGB(255, 255, 255);
                itemInHand = new ItemStack(Material.MILK_BUCKET);
                /*
                /summon armor_stand ~ ~ ~ {NoBasePlate:1b,ShowArms:1b,Small:1b,Pose:{Head:[343f,0f,346f]
                ,LeftLeg:[352f,331f,344f],RightLeg:[0f,24f,22f],LeftArm:[219f,335f,32f],RightArm:[245f,54f,325f]}}
                 */
                //this.customStand.setBodyPose(new CraftVector());
                this.customStand.setHeadPose(new CraftVector(343f,0f,346f));
                this.customStand.setLeftLegPose(new CraftVector(352f,331f,344f));
                this.customStand.setRightLegPose(new CraftVector(0f,24f,22f));
                this.customStand.setLeftArmPose(new CraftVector(219f,335f,32f));
                this.customStand.setRightArmPose(new CraftVector(245f,54f,325f));
                break;
            }
            case 5: {
                color = Color.fromRGB(216, 131, 233);
                itemInHand = new ItemStack(Material.EXP_BOTTLE);
                /*
                /summon armor_stand ~ ~ ~ {NoBasePlate:1b,ShowArms:1b,Small:1b,Pose:{Body:[6f,0f,0f],Head:[311f,356f,0f],LeftLeg:[20f,329f,341f],
                RightLeg:[341f,28f,14f],LeftArm:[249f,30f,0f],RightArm:[245f,0f,44f]}}
                 */
                this.customStand.setBodyPose(new CraftVector(6f,0f,0f));
                this.customStand.setHeadPose(new CraftVector(311f,356f,0f));
                this.customStand.setLeftLegPose(new CraftVector(20f,335f,341f));
                this.customStand.setRightLegPose(new CraftVector(341f,28f,14f));
                this.customStand.setLeftArmPose(new CraftVector(249f,30f,0f));
                this.customStand.setRightArmPose(new CraftVector(245f,0f,44f));
                break;
            }
            default: {
                color = Color.fromRGB(0, 0, 0);
                itemInHand = new ItemStack(Material.BARRIER);
                break;
            }
        }
        final EntityEquip equip = this.customStand.getEntityEquip();
        equip.setChestplate(ItemUtil.getBuilder(Material.LEATHER_CHESTPLATE).setColor(color).build());
        equip.setLeggings(ItemUtil.getBuilder(Material.LEATHER_LEGGINGS).setColor(color).build());
        equip.setBoots(ItemUtil.getBuilder(Material.LEATHER_BOOTS).setColor(color).build());
        equip.setHelmet(ItemUtil.getBuilder(Head.LOBBY_ANOTHER).build());
        equip.setItemInMainHand(itemInHand);
    }

    public void removeTo(final BukkitGamer gamer, final boolean hideStand) {
        if (gamer == null) {
            return;
        }
        final Hologram hologram = this.holograms.get(0);
        if (hologram != null) {
            hologram.removeTo(gamer);
        } else {
            this.holograms.get(0).removeTo(gamer);
        }
        if (hideStand) {
            this.customStand.removeTo(gamer);
        }
    }

    public void showTo(final BukkitGamer gamer, final boolean showStand) {
        if (gamer == null) {
            return;
        }
        final Hologram hologram = this.holograms.get(0);
        if (hologram != null) {
            hologram.showTo(gamer);
        } else {
            this.holograms.get(0).showTo(gamer);
        }
        if (showStand) {
            this.customStand.showTo(gamer);
        }
    }

    public int getPosition() {
        return this.position;
    }

    public Top getTopType() {
        return this.topType;
    }

    public CustomStand getCustomStand() {
        return this.customStand;
    }

    public Map<Integer, Hologram> getHolograms() {
        return this.holograms;
    }

    public StandTopData getStandTopData() {
        return this.standTopData;
    }
}