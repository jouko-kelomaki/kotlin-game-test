package fi.kelomaki.keloGame.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class MoveArrowComponent : Component, Pool.Poolable {
    override fun reset() {
        TODO("Not yet implemented")
    }


    companion object {
        val mapper = mapperFor<MoveArrowComponent>()
    }
}