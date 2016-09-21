package com.inbar.feedme;


import graphql.schema.*;

import static graphql.Scalars.GraphQLBoolean;
import static graphql.Scalars.GraphQLFloat;
import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLEnumType.newEnum;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInterfaceType.newInterface;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Inbar on 9/21/2016.
 */

public class FeedmeSchema {

    public static GraphQLEnumType unitsEnum = newEnum()
            .name("Units")
            .description("Measurment units for ingredients")
            .value("cups", 0, "Cups")
            .value("tbsps", 1, "Table spoons")
            .value("tsps", 2, "Teaspoons")
            .value("drops", 3, "Drops")
            .value("ml", 4, "Milliliters")
            .value("liter", 6, "Liter")
            .value("fl oz", 5, "Fluid Ounces")
            .value("pints", 7, "Pints")
            .value("quarts", 8, "Quarts")
            .value("gallons", 8, "Gallons")
            .value("mg", 9, "Milligrams")
            .value("g", 10, "Grams")
            .value("kg", 11, "Kilograms")
            .value("oz", 12, "Ounces")
            .value("pounds", 13, "Pounds")
            .value("\"", 14, "Inches")
            .value("cm", 15, "Centimeters")
            .value("dash", 16, "/Dash - a very small pinch")
            .value("pinch", 17, "Pinch")
            .value("cel", 18, "Degrees Celsius")
            .value("far", 19, "Degrees Fahrenheits")
            .build();


    public static GraphQLObjectType ingredientType = newObject()
            .name("Ingrdient")
            .field(newFieldDefinition()
                    .name("name")
                    .type(GraphQLString)
                    .build())
            .field(newFieldDefinition()
                    .name("amount")
                    .type(GraphQLFloat)
                    .build())
            .field(newFieldDefinition()
                    .name("units")
                    .type(unitsEnum)
                    .build())
            .build();

    public static GraphQLObjectType recipeType = newObject()
            .name("Recipe")
            .description("A recipe")
            .field(newFieldDefinition()
                    .name("id")
                    .description("The id of the recipe.")
                    .type(new GraphQLNonNull(GraphQLLong))
                    .build())
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the recipe.")
                    .type(GraphQLString)
                    .build())
            .field(newFieldDefinition()
                    .name("prepTime")
                    .description("The time it takes to prepare ths recipe.")
                    .type(GraphQLInt)
                    .build())
            .field(newFieldDefinition()
                    .name("drawableThumbnail")
                    .description("The ID of the drawable thumbnail.")
                    .type(GraphQLInt)
                    .build())
            .field(newFieldDefinition()
                    .name("favorite")
                    .description("Indicator whether favored by user or not.")
                    .type(GraphQLBoolean)
                    .build())
            .field(newFieldDefinition()
                    .name("ingredients")
                    .description("List of ingredients.")
                    .type(new GraphQLList(ingredientType))
                    .build())
            .field(newFieldDefinition()
                    .name("instructions")
                    .description("List of instructions.")
                    .type(new GraphQLList(GraphQLString))
                    .build())
            .build();

    public static GraphQLObjectType storyType = newObject()
            .name("Story")
            .description("A story")
            .field(newFieldDefinition()
                    .name("id")
                    .description("The id of the story.")
                    .type(new GraphQLNonNull(GraphQLLong))
                    .build())
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the story.")
                    .type(GraphQLString)
                    .build())
            .field(newFieldDefinition()
                    .name("readTime")
                    .description("The time it takes to read ths story.")
                    .type(GraphQLInt)
                    .build())
            .field(newFieldDefinition()
                    .name("drawableThumbnail")
                    .description("The ID of the drawable thumbnail.")
                    .type(GraphQLInt)
                    .build())
            .field(newFieldDefinition()
                    .name("paragraphs")
                    .description("List of paragraphs.")
                    .type(new GraphQLList(GraphQLString))
                    .build())
            .build();
/*
    public static GraphQLObjectType humanType = newObject()
            .name("Human")
            .description("A humanoid creature in the Star Wars universe.")
            .withInterface(recipeInterface)
            .field(newFieldDefinition()
                    .name("id")
                    .description("The id of the human.")
                    .type(new GraphQLNonNull(GraphQLString)))
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the human.")
                    .type(GraphQLString))
            .field(newFieldDefinition()
                    .name("friends")
                    .description("The friends of the human, or an empty list if they have none.")
                    .type(new GraphQLList(recipeInterface))
                    .dataFetcher(StarWarsData.getFriendsDataFetcher()))
            .field(newFieldDefinition()
                    .name("appearsIn")
                    .description("Which movies they appear in.")
                    .type(new GraphQLList(episodeEnum)))
            .field(newFieldDefinition()
                    .name("homePlanet")
                    .description("The home planet of the human, or null if unknown.")
                    .type(GraphQLString))
            .build();

    public static GraphQLObjectType droidType = newObject()
            .name("Droid")
            .description("A mechanical creature in the Star Wars universe.")
            .withInterface(recipeInterface)
            .field(newFieldDefinition()
                    .name("id")
                    .description("The id of the droid.")
                    .type(new GraphQLNonNull(GraphQLString)))
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the droid.")
                    .type(GraphQLString))
            .field(newFieldDefinition()
                    .name("friends")
                    .description("The friends of the droid, or an empty list if they have none.")
                    .type(new GraphQLList(recipeInterface))
                    .dataFetcher(FeedMeData.getFriendsDataFetcher()))
            .field(newFieldDefinition()
                    .name("appearsIn")
                    .description("Which movies they appear in.")
                    .type(new GraphQLList(episodeEnum)))
            .field(newFieldDefinition()
                    .name("primaryFunction")
                    .description("The primary function of the droid.")
                    .type(GraphQLString))
            .build();
*/

    public static GraphQLObjectType queryType = newObject()
            .name("QueryType")
            .field(newFieldDefinition()
                    .name("recipe")
                    .type(recipeType)
                    .argument(newArgument()
                            .name("id")
                            .description("id of the recipe.")
                            .type(new GraphQLNonNull(GraphQLLong)))
                    .dataFetcher(FeedMeData.getRecipeDataFetcher()))
            .field(newFieldDefinition()
                    .name("story")
                    .type(storyType)
                    .argument(newArgument()
                            .name("id")
                            .description("id of the story")
                            .type(new GraphQLNonNull(GraphQLString)))
                    .dataFetcher(FeedMeData.getStoryDataFetcher()))
            /*.field(newFieldDefinition()
                    .name("droid")
                    .type(droidType)
                    .argument(newArgument()
                            .name("id")
                            .description("id of the droid")
                            .type(new GraphQLNonNull(GraphQLString)))
                    .dataFetcher(FeedMeData.getDroidDataFetcher()))*/
            .build();


    public static GraphQLSchema feedMeSchema = GraphQLSchema.newSchema()
            .query(queryType)
            .build();
}

