package com.github.technus.tectech.thing.metaTileEntity.multi;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.textureOffset;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsTT;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockUnlocalizedName;

import static gregtech.api.enums.GT_HatchElement.InputHatch;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;
import static gregtech.api.util.GT_StructureUtility.ofFrame;
import static net.minecraft.util.StatCollector.translateToLocal;


import com.github.technus.tectech.TecTech;
import com.github.technus.tectech.thing.gui.TecTechUITextures;
import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.forge.ItemStackHandler;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.api.widget.Widget;
import com.gtnewhorizons.modularui.common.internal.wrapper.BaseSlot;
import com.gtnewhorizons.modularui.common.widget.ButtonWidget;
import com.gtnewhorizons.modularui.common.widget.ChangeableWidget;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedRow;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.Scrollable;
import com.gtnewhorizons.modularui.common.widget.SlotGroup;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;


import com.github.technus.tectech.Reference;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GT_MetaTileEntity_EM_quantumBank extends GT_MetaTileEntity_MultiblockBase_EM
        implements ISurvivalConstructable {
    private int cappedStorage = 200;
    @Override
    public void onFirstTick_EM(IGregTechTileEntity aBaseMetaTileEntity) {
        inventoryHandler.setSize(250);
        if (!hasMaintenanceChecks) turnOffMaintenance();
        if (!mMachine) {
            aBaseMetaTileEntity.disableWorking();
        }
    }
    // region structure
    private static final String[] description = new String[] {
            EnumChatFormatting.AQUA + translateToLocal("tt.keyphrase.Hint_Details") + ":",
            translateToLocal("gt.blockmachines.multimachine.em.quantumbank.hint.0"),
    };

    private static final IStructureDefinition<GT_MetaTileEntity_EM_quantumBank> STRUCTURE_DEFINITION = IStructureDefinition
            .<GT_MetaTileEntity_EM_quantumBank>builder()
            .addShape(
                    "main",
                    transpose(
                            new String[][]{
                                    {"DGGGGGGGD","DE     ED","DE     ED","DE     ED","DGGGGGGGD"},
                                    {"DE     ED","DA     AD","DABFFFBAD","DA     AD","DE     ED"},
                                    {"D~     ED","DABFFFBAD","DACCCCCAD","DABFFFBAD","DE     ED"},
                                    {"DE     ED","DA     AD","DABFFFBAD","DA     AD","DE     ED"},
                                    {"DGGGGGGGD","DE     ED","DE     ED","DE     ED","DGGGGGGGD"}
                            }))
            .addElement('A', ofBlock(GregTech_API.sBlockCasings1, 12))
            .addElement('B', ofBlock(GregTech_API.sBlockCasings1, 13))
            .addElement('C', ofBlock(GregTech_API.sBlockCasings1, 14))
            .addElement('D', ofBlock(sBlockCasingsTT, 2))
            .addElement('F', ofBlockUnlocalizedName("tectech","tile.quantumGlass",0))
            .addElement('G', ofFrame(MaterialsUEVplus.TranscendentMetal))
            .addElement('E',
                    buildHatchAdder(GT_MetaTileEntity_EM_quantumBank.class)
                            .atLeast(GT_MetaTileEntity_EM_quantumBank.HatchElement.InputData,InputHatch)
                            .casingIndex(textureOffset + 3).dot(1).buildAndChain(ofBlock(sBlockCasingsTT, 3)))
            .build();
    // endregion

    public GT_MetaTileEntity_EM_quantumBank(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        turnOffMaintenance();
    }

    public GT_MetaTileEntity_EM_quantumBank(String aName) {
        super(aName);
        turnOffMaintenance();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {

        return new GT_MetaTileEntity_EM_quantumBank(mName);
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.name"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.0"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.1"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.2"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.3"))
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.quantumbank.desc.4"))
                .addSeparator().beginStructureBlock(9, 5, 5, false)
                .addOtherStructurePart(
                        translateToLocal("gt.blockmachines.hatch.datain.tier.07.name"),
                        translateToLocal("tt.keyword.Structure.AnyHighPowerCasing1D"), 1) // Data Bank Slave Connector: Any Computer Casing
                .addInputHatch("Any High Power Casing with 1 dot",1) //Unsure why it won't use lang, maybe wrong func call?
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        if (!structureCheck_EM("main", 1, 2, 0)){
            return false;
        }
        return true;
    }


    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
                                 int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.casingTexturePages[texturePage][3],
                    new TT_RenderedExtendedFacingTexture(
                            aActive ? GT_MetaTileEntity_MultiblockBase_EM.ScreenON
                                    : GT_MetaTileEntity_MultiblockBase_EM.ScreenOFF) };
        }
        return new ITexture[] { Textures.BlockIcons.casingTexturePages[texturePage][3] };
    }

    public static final ResourceLocation activitySound = new ResourceLocation(Reference.MODID + ":fx_hi_freq");

    @Override
    @SideOnly(Side.CLIENT)
    protected ResourceLocation getActivitySound() {
        return activitySound;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM("main", 1, 2, 0, stackSize, hintsOnly);
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_EM_quantumBank> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return description;
    }

    @Override
    public boolean isPowerPassButtonEnabled() {
        return false;
    }

    @Override
    public boolean isSafeVoidButtonEnabled() {
        return false;
    }

    @Override
    public boolean isAllowedToWorkButtonEnabled() {
        return true;
    }
    @Override
    @NotNull
    protected CheckRecipeResult checkProcessing_EM() {
        return SimpleCheckRecipeResult.ofFailure("no_data");
    }
    @Override
    public void addGregTechLogo(ModularWindow.Builder builder) { //Moving this stupid ass logo in the way
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.PICTURE_TECTECH_LOGO_DARK).setSize(18, 18)
                        .setPos(173, 184));
    }
    @Override
    protected ButtonWidget createPowerSwitchButton() { //We have to override this because .setPos on our inherited clashes since its predefined :fail:
        Widget button = new ButtonWidget().setOnClick((clickData, widget) -> {
            if (isAllowedToWorkButtonEnabled()) {
                TecTech.proxy.playSound(getBaseMetaTileEntity(), "fx_click");
                if (getBaseMetaTileEntity().isAllowedToWork()) {
                    getBaseMetaTileEntity().disableWorking();
                } else {
                    getBaseMetaTileEntity().enableWorking();
                }
            }
        }).setPlayClickSound(false).setBackground(() -> {
            List<UITexture> ret = new ArrayList<>();
            ret.add(TecTechUITextures.BUTTON_STANDARD_16x16);
            if (!isAllowedToWorkButtonEnabled()) {
                ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_DISABLED);
            } else {
                if (getBaseMetaTileEntity().isAllowedToWork()) {
                    ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_ON);
                } else {
                    ret.add(TecTechUITextures.OVERLAY_BUTTON_POWER_SWITCH_OFF);
                }
            }
            return ret.toArray(new IDrawable[0]);
        }).setPos(174,242).setSize(16, 16);
        if (isAllowedToWorkButtonEnabled()) {
            button.addTooltip("Power Switch").setTooltipShowUpDelay(TOOLTIP_DELAY);
        }
        return (ButtonWidget) button;
    }

    @Override
    public void bindPlayerInventoryUI(ModularWindow.Builder builder, UIBuildContext buildContext) {
        builder.bindPlayerInventory(
                buildContext.getPlayer(),
                new Pos2d(7, 183), //Inventory POS (PLAYERS)
                this.getGUITextureSet()
                        .getItemSlot());
    }
    private DynamicPositionedColumn drawTexts(DynamicPositionedColumn screenElements){
        screenElements.setSynced(false)
                .setSpace(0)
                .setPos(10,7);
        screenElements.widget(new TextWidget("§aProviding Data"));
        screenElements.widget(new TextWidget("Number of Data Sticks: " + 1)
                .setDefaultColor(COLOR_TEXT_WHITE.get()));
        screenElements.widget(new TextWidget("Current Capacity: " + inventoryHandler.getSlots())
                .setDefaultColor(COLOR_TEXT_WHITE.get()));
        return screenElements;
    }
    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        final DynamicPositionedColumn screenElements = new DynamicPositionedColumn();
        builder.setSize(new Size(200, 265));
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE).setPos(4, 4)
                        .setSize(190, 91));
        builder.widget(
                new DrawableWidget().setDrawable(TecTechUITextures.BACKGROUND_SCREEN_BLUE).setPos(4, 98)
                        .setSize(190, 81));
        drawTexts(screenElements);
        builder.widget(screenElements.setPos(7, 8));




        Widget powerSwitchButton = createPowerSwitchButton();
        builder.widget(powerSwitchButton)
                .widget(new FakeSyncWidget.BooleanSyncer(() -> getBaseMetaTileEntity().isAllowedToWork(), val -> {
                    if (val) getBaseMetaTileEntity().enableWorking();
                    else getBaseMetaTileEntity().disableWorking();
                }));

        final Scrollable scrollable = new Scrollable().setVerticalScroll();
        for (int row = 0; row * 10 < cappedStorage - 1; row++) {
            int columnsToMake = Math.min(cappedStorage - row * 10, 10);
            for (int column = 0; column < columnsToMake; column++) {
                scrollable.widget(
                        new SlotWidget(inventoryHandler, row * 10 + column).setPos(column * 18, row * 18)
                                .setSize(18, 18));
            }
        }
        builder.widget(scrollable.setSize(18 * 10 + 4, 18 * 4).setPos(7, 103));

    }


    }

