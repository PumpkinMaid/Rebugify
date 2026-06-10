package com.rebugify.mixin.cachePoisoningEnabled;

import com.mojang.authlib.yggdrasil.TextureUrlChecker;
import com.rebugify.Rebugify;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.IDN;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

@Mixin(TextureUrlChecker.class)
public abstract class TextureUrlCheckerMixin {
    @Unique
    private static final List<String> ALLOWED_DOMAINS = List.of(
            ".minecraft.net",
            ".mojang.com"
    );

    @Unique
    private static final List<String> BLOCKED_DOMAINS = List.of(
            "bugs.mojang.com",
            "education.minecraft.net",
            "feedback.minecraft.net"
    );

    @Inject(method = "isAllowedTextureDomain", at = @At("HEAD"), cancellable = true)
    private static void rebugify$cachePoisoningEnabled(String url, CallbackInfoReturnable<Boolean> cir) {
        if (!Rebugify.CONFIG.cachePoisoningEnabled.get()) return;

        final URI uri;
        try {
            uri = new URI(url).normalize();
        } catch (final URISyntaxException ignored) {
            cir.setReturnValue(false);
            return;
        }

        final String scheme = uri.getScheme();
        if (scheme == null || !List.of("http", "https").contains(scheme)) {
            cir.setReturnValue(false);
            return;
        }

        final String domain = uri.getHost();
        if (domain == null) {
            cir.setReturnValue(false);
            return;
        }

        final String decodedDomain = IDN.toUnicode(domain);
        if (!decodedDomain.toLowerCase(Locale.ROOT).equals(decodedDomain)) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(rebugify$isDomainOnList(decodedDomain, ALLOWED_DOMAINS) && !rebugify$isDomainOnList(decodedDomain, BLOCKED_DOMAINS)
        );
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