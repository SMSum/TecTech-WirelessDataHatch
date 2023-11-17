package com.github.technus.tectech.thing.multitileentity;

import com.github.technus.tectech.thing.multitileentity.logic.dataBank_Logic;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.util.Vec3Impl;
import gregtech.api.multitileentity.multiblock.base.Controller;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.multitileentity.multiblock.base.MultiBlockPart.ITEM_IN;
import static gregtech.api.util.GT_StructureUtilityMuTE.ofMuTECasings;

public class GT_MultiTileEntity_TT_dataBank extends Controller<GT_MultiTileEntity_TT_dataBank, dataBank_Logic> {

    @Override
    public short getCasingRegistryID() {
        return 0;
    }

    @Override
    public int getCasingMeta() {
        return 0;
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        return null;
    }

    @Override
    public Vec3Impl getStartingStructureOffset() {
        return null;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {

    }

    @Override
    public IStructureDefinition getStructureDefinition() {
        return null;
    }

    @NotNull
    @Override
    protected dataBank_Logic createProcessingLogic() {
        return null;
    }
}
