package com.takoy3466.manaitapp.init;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.dataComponent.InvincibleData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentsInit {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Manaitapp.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<InvincibleData>> INVINCIBLE_ATTACHMENT = ATTACHMENTS.register("invincible_attachment",
            () -> AttachmentType.builder(() -> new InvincibleData(false)).serialize(InvincibleData.CODEC).build()
    );
}
