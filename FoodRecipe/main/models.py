from django.db import models

# Create your models here.
class Item(models.Model):
    item_title = models.CharField(max_length= 50)

    def __str__(self):
        return self.item_title



class Recipe(models.Model):
	recipe_title = models.CharField(max_length = 50)
	recipe_description = models.TextField()
	recipe_category = models.CharField(max_length = 50)
	recipe_ingredients = models.CharField(max_length = 50)
	recipe_images = models.ImageField(upload_to='images')
	recipe_videos = models.FileField(upload_to = 'videos')
	recipe_favorites = models.BooleanField()

	def __str__(self):
		return self.recipe_title


class Comment(models.Model):
	commentor = models.TextField()
	item = models.ForeignKey(Item, on_delete=models.CASCADE)
	recipes = models.ManyToManyField(Recipe)

	def __str__(self):
		return self.commentor
