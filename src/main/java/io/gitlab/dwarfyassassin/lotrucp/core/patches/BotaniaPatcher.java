package io.gitlab.dwarfyassassin.lotrucp.core.patches;

import org.objectweb.asm.tree.*;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.*;
import io.gitlab.dwarfyassassin.lotrucp.core.utils.ASMUtils;

public class BotaniaPatcher extends ModPatcher {
	public BotaniaPatcher() {
		super("Botania", "Botania");
		classes.put("vazkii.botania.common.block.subtile.generating.SubTileKekimurus", new Patcher.ConsumerImplBecauseNoLambdas<ClassNode>() {

			@Override
			public void accept(ClassNode node) {
				BotaniaPatcher.this.patchKekimurus(node);
			}
		});
	}

	private void patchKekimurus(ClassNode classNode) {
		MethodNode method = ASMUtils.findMethod(classNode, "onUpdate", "()V");
		if (method == null) {
			return;
		}
		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (!(node instanceof TypeInsnNode)) {
				continue;
			}
			TypeInsnNode typeNode = (TypeInsnNode) node;
			if (!typeNode.desc.equals("net/minecraft/block/BlockCake")) {
				continue;
			}
			typeNode.desc = "lotr/common/block/LOTRBlockPlaceableFood";
			break;
		}
		UCPCoreMod.log.info("Patched the Kekimurus to eat all LOTR cakes.");
	}

}
