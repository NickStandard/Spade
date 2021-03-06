package net.nexisonline.spade.chunkproviders;

import java.util.logging.Logger;

import net.nexisonline.spade.SpadeChunkProvider;

import org.bukkit.block.Biome;
import org.bukkit.util.config.ConfigurationNode;

public class ChunkProviderFlatGrass extends SpadeChunkProvider {
	private byte[] template;
	
	public void onLoad(Object world, long seed) {
		this.setHasCustomTerrain(true);
		
		template=new byte[16*128*16];
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				double h = 64;
				for (int y = 0; y < h; y++) {
					template[getBlockIndex(x, y, z)] = (byte) ((y < 2) ? 7 : 1);
				}
			}
		}
	}

	@Override
	public void generateChunk(Object world, int X, int Z, byte[] abyte,
			Biome[] biomes, double[] temperature) {
		abyte=template;
		if(X==0&&Z==0) {
			int z=0;
			int x=0;
			for (int y = 0; y < 64; y++) {
				// Origin point + sand to prevent 5000 years of loading.
				if(x==0&&z==0&&X==x&&Z==z&&y<=63)
					abyte[getBlockIndex(x, y, z)]=(byte) ((y==63)?12:7);
			}

		}
		Logger.getLogger("Minecraft")
				.info(String.format("[Flatgrass] Chunk (%d,%d)", X, Z));
	}

	@Override
	public ConfigurationNode configure(ConfigurationNode node) {
		return node;
	}

}
