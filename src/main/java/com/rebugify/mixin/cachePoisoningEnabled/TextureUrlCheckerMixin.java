package com.rebugify.mixin.cachePoisoningEnabled;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.yggdrasil.TextureUrlChecker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.Set;

// This is purely theoretical as I have no way to test it (yet). This is based on my understanding of the exploit, if it doesn't work, please make an issue. I doubt this will get any use regardless.
@Mixin(TextureUrlChecker.class)
public abstract class TextureUrlCheckerMixin {
    @Final
    @Shadow
    private static List<String> ALLOWED_DOMAINS;

    @Unique
    private static final List<String> BLOCKED_DOMAINS = List.of(
            "bugs.mojang.com",
            "education.minecraft.net",
            "feedback.minecraft.net"
    );

    @Unique
    private static String decodedDomain;

    @ModifyVariable(method = "<clinit>", at = @At("HEAD"))
    private static Set<String> rebugify$modifyAllowedDomains() {
        return Set.of(
                ".minecraft.net",
                ".mojang.com"
        );
    }

    @ModifyVariable(method = "isAllowedTextureDomain", at = @At(value = "STORE"), name = "decodedDomain")
    private static String rebugify$returnDecodedDomain(String decodedDomain_) {
        decodedDomain = decodedDomain_;
        return decodedDomain;
    }

    @ModifyReturnValue(method = "isAllowedTextureDomain", at = @At(value = "RETURN", ordinal = 3))
    private static boolean rebugify$cachePoisoningEnabled(boolean original) {
        return rebugify$isDomainOnList(decodedDomain, ALLOWED_DOMAINS) && !rebugify$isDomainOnList(decodedDomain, BLOCKED_DOMAINS);
    }

    @Unique
    private static boolean rebugify$isDomainOnList(final String domain, final List<String> list) {
        for (final String entry : list) {
            if (domain.endsWith(entry)) {
                return true;
            }
        }
        return false;
    }
}