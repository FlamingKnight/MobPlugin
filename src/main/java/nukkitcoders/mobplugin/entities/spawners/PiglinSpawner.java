package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.mob.EntityPiglin;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.utils.Utils;

public class PiglinSpawner extends AbstractEntitySpawner {

    public PiglinSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        if (pos.y < 128 && pos.y > 0 && level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == Block.NETHERRACK && level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z) < 8) {
            for (int i = 0; i < 4; i++) {
                BaseEntity entity = this.spawnTask.createEntity("Piglin", pos.add(0, 1, 0));
                if (entity == null) return;
                if (Utils.rand(1, 20) == 1) {
                    if (entity == null) return;
                    entity.setBaby(true);
                }
            }
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return EntityPiglin.NETWORK_ID;
    }
}
