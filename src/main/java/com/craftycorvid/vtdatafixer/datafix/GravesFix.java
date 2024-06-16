package com.craftycorvid.vtdatafixer.datafix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;


public class GravesFix extends DataFix {
        public GravesFix(Schema outputSchema) {
                super(outputSchema, true);
        }

        @Override
        public TypeRewriteRule makeRule() {
                return null;
        }
}
