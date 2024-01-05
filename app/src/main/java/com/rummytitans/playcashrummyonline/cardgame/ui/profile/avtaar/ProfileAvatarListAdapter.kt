package com.rummytitans.playcashrummyonline.cardgame.ui.profile.avtaar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rummytitans.playcashrummyonline.cardgame.databinding.ItemAvtaarListBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseViewHolder
import com.rummytitans.playcashrummyonline.cardgame.utils.SpacesItemDecoration


class ProfileAvatarListAdapter(var listResponse: MutableList<ProfileAvtarModel>,
                               val callback: AvtarNavigator
) : RecyclerView.Adapter<BaseViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val avatarBinding = ItemAvtaarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return AvatarViewHolder(avatarBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return listResponse.size ?: 0
    }

    fun setSelected(position: Int) {
        val avatarId = if(position == 0)1 else position
        listResponse.map {
            it.avtarList.map { avtarItem->
                avtarItem.selected = avtarItem.avtarId == avatarId
            }
        }
        notifyDataSetChanged()
    }

    fun updateItems(listResponse: MutableList<ProfileAvtarModel>) {
        this.listResponse.clear()
        this.listResponse = listResponse
        notifyDataSetChanged()
    }

    inner class AvatarViewHolder internal constructor(internal var mBinding: ItemAvtaarListBinding) :
        BaseViewHolder(mBinding.root), AvtarNavigator {

        override fun onBind(position: Int) {
            val model = listResponse[position]
            mBinding.avtar = listResponse[position]
            mBinding.rvNewAvatars.itemAnimator = null
            mBinding.rvNewAvatars.adapter = ProfileAvatarAdapter(model.avtarList,this)
            if(mBinding.rvNewAvatars.itemDecorationCount == 0){
                mBinding.rvNewAvatars.addItemDecoration(SpacesItemDecoration(5))
            }
            mBinding.executePendingBindings()
        }

        override fun onSelectAvtar(id: Int) {
            callback.onSelectAvtar(id)
            val preSelIndex = listResponse.indexOfFirst{it.isAvtarSelected()}
            if(preSelIndex >= 0){
                listResponse[preSelIndex].avtarList.map{
                    it.selected = it.avtarId == id
                }
            }
            notifyItemChanged(preSelIndex)
        }
    }
}
