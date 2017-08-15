package com.cjp.effects.particle.explosion;

import android.app.Activity;
import android.os.Bundle;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2017/7/4.
 * email : caijinpin@zhexinit.com
 * 来自  http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/1204/3747.html
 * github https://github.com/tyrantgit/ExplosionField
 */
public class ParticleExplosionActivity extends Activity {
    public static final String TAG = "ParticleExplosionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle_explosion);

        ExplosionField explosionField = new ExplosionField(this);

        explosionField.addListener(findViewById(R.id.root));
    }

}
