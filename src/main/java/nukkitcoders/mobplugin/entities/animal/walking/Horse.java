package nukkitcoders.mobplugin.entities.animal.walking;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.data.IntEntityData;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import nukkitcoders.mobplugin.entities.HorseBase;
import nukkitcoders.mobplugin.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class Horse extends HorseBase {

    public static final int NETWORK_ID = 23;

    private int variant = this.getRandomVariant();

    public Horse(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        if (this.isBaby()) {
            return 0.6982f;
        }
        return 1.3965f;
    }

    @Override
    public float getHeight() {
        if (this.isBaby()) {
            return 0.8f;
        }
        return 1.6f;
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(15);

        if (this.namedTag.contains("Variant")) {
            this.variant = this.namedTag.getInt("Variant");
        } else {
            this.namedTag.putInt("Variant", this.variant = this.getRandomVariant());
        }

        this.setDataProperty(new IntEntityData(DATA_VARIANT, this.variant));
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putInt("Variant", this.variant);
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        if (creature instanceof Player) {
            Player player = (Player) creature;
            return player.spawned && player.isAlive() && !player.closed
                    && (player.getInventory().getItemInHand().getId() == Item.WHEAT
                    || player.getInventory().getItemInHand().getId() == Item.APPLE
                    || player.getInventory().getItemInHand().getId() == Item.HAY_BALE
                    || player.getInventory().getItemInHand().getId() == Item.GOLDEN_APPLE
                    || player.getInventory().getItemInHand().getId() == Item.SUGAR
                    || player.getInventory().getItemInHand().getId() == Item.BREAD
                    || player.getInventory().getItemInHand().getId() == Item.GOLDEN_CARROT)
                    && distance <= 49;
        }
        return false;
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();

        if (this.hasCustomName()) {
            drops.add(Item.get(Item.NAME_TAG, 0, 1));
        }

        if (this.lastDamageCause instanceof EntityDamageByEntityEvent && !this.isBaby()) {
            for (int i = 0; i < Utils.rand(0, 2); i++) {
                drops.add(Item.get(Item.LEATHER, 0, 1));
            }
        }

        return drops.toArray(new Item[0]);
    }


    private int getRandomVariant() {
        int[] variantList = {0, 1, 2, 3, 4, 5, 6, 256, 257, 258, 259, 260, 261, 262, 512, 513, 514, 515, 516, 517, 518,
                768, 769, 770, 771, 772, 773, 774, 1024, 1025, 1026, 1027, 1028, 1029, 1030};
        return variantList[Utils.rand(0, variantList.length - 1)];
    }
}
