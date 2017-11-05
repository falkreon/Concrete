/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017:
 * 	William Thompson (unascribed),
 * 	Isaac Ellingson (Falkreon),
 * 	Jamie Mansfield (jamierocks),
 * 	and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.elytradev.concrete.recipe.impl;

import java.util.ArrayList;
import java.util.List;

import com.elytradev.concrete.recipe.ItemIngredient;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ShapelessInventoryRecipe extends InventoryGridRecipe {
	private List<ItemIngredient> input = new ArrayList<>();
	private ItemStack output;
	
	public ShapelessInventoryRecipe(ItemStack output, ItemIngredient... ingredients) {
		for(ItemIngredient item : ingredients) input.add(item);
	}
	
	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public boolean matches(ItemStackHandler handler) {
		List<ItemIngredient> toFind = new ArrayList<>(input);
		
		for(int i=0; i<handler.getSlots(); i++) {
			ItemStack stack = handler.getStackInSlot(i);
			ItemIngredient found = null;
			for(ItemIngredient ingredient : toFind) {
				if (ingredient.apply(stack)) {
					found = ingredient;
					break;
				}
			}
			if (found==null) return false; //We have ingredients in the handler that aren't in the recipe
			toFind.remove(found);
		}
		return toFind.isEmpty(); //We have ingredients in the recipe that aren't in the handler
	}

	@Override
	public boolean matches(IInventory inventory) {
		List<ItemIngredient> toFind = new ArrayList<>(input);
		
		for(int i=0; i<inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			ItemIngredient found = null;
			for(ItemIngredient ingredient : toFind) {
				if (ingredient.apply(stack)) {
					found = ingredient;
					break;
				}
			}
			if (found==null) return false; //We have ingredients in the handler that aren't in the recipe
			toFind.remove(found);
		}
		return toFind.isEmpty(); //We have ingredients in the recipe that aren't in the handler
	}

}
