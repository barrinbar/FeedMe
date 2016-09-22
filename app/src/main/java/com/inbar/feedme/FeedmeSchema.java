package com.inbar.feedme;


import java.util.ArrayList;
import java.util.List;

import graphql.schema.*;

import static graphql.Scalars.GraphQLBoolean;
import static graphql.Scalars.GraphQLFloat;
import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLEnumType.newEnum;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Inbar on 9/21/2016.
 *
 * https://github.com/graphql-java/graphql-java
 * https://github.com/chentsulin/awesome-graphql
 * https://github.com/graphql-java/graphql-java/blob/master/src/test/groovy/graphql/StarWarsSchema.java
 * https://github.com/graphql-java/graphql-java/blob/master/src/test/groovy/graphql/GarfieldSchema.java
 *
 * TODO: Check why I need build() in every field
 */

public class FeedmeSchema {

    public static GraphQLEnumType unitsEnum = newEnum()
            .name("Units")
            .description("Measurement units for ingredients")
            .value("cups",      0, "Cups")
            .value("tbsps",     1, "Table spoons")
            .value("tsps",      2, "Teaspoons")
            .value("drops",     3, "Drops")
            .value("ml",        4, "Milliliters")
            .value("liter",     5, "Liter")
            .value("fl oz",     6, "Fluid Ounces")
            .value("pints",     7, "Pints")
            .value("quarts",    8, "Quarts")
            .value("gallons",   9, "Gallons")
            .value("mg",        10, "Milligrams")
            .value("g",         11, "Grams")
            .value("kg",        12, "Kilograms")
            .value("oz",        13, "Ounces")
            .value("pounds",    14, "Pounds")
            .value("\"",        15, "Inches")
            .value("cm",        16, "Centimeters")
            .value("dash",      17, "Dash - a very small pinch")
            .value("pinch",     18, "Pinch")
            .value("cel",       19, "Degrees Celsius")
            .value("far",       20, "Degrees Fahrenheits")
            .value("unit",      21, "General unit")
            .value("bundle",    22, "General unit")
            .build();

    public static GraphQLObjectType ingredientType = newObject()
            .name("Ingredient")
            .description("A composed ingredient")
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

    public static GraphQLObjectType storyType = newObject()
            .name("Story")
            .description("A story")
            .field(newFieldDefinition()
                    .name("id")
                    .description("Story id.")
                    .type(new GraphQLNonNull(GraphQLLong))
                    .build())
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the story.")
                    .type(GraphQLString)
                    .build())
            .field(newFieldDefinition()
                    .name("author")
                    .description("The author(s) of the story.")
                    .type(GraphQLString)
                    .build())
            .field(newFieldDefinition()
                    .name("thumbnail")
                    .description("The ID of the drawable thumbnail.")
                    .type(GraphQLInt)
                    .build())
            .field(newFieldDefinition()
                    .name("paragraphs")
                    .description("List of paragraphs.")
                    .type(new GraphQLList(GraphQLString))
                    .build())
            .build();

    public static GraphQLObjectType recipeType = newObject()
            .name("Recipe")
            .description("A recipe")
            .field(newFieldDefinition()
                    .name("id")
                    .description("Recipe id.")
                    .type(new GraphQLNonNull(GraphQLLong))
                    .build())
            .field(newFieldDefinition()
                    .name("name")
                    .description("Recipe name.")
                    .type(GraphQLString)
                    .build())
            .field(newFieldDefinition()
                    .name("prepTime")
                    .description("The time it takes to prepare ths recipe.")
                    .type(GraphQLInt)
                    .build())
            .field(newFieldDefinition()
                    .name("thumbnail")
                    .description("ID of the drawable thumbnail.")
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
            .field(newFieldDefinition()
                    .name("recipe")
                    .description("Related recipe.")
                    .type(storyType)
                    .build())
            .build();

    public static List<GraphQLArgument> recipeArgs() {
        List<GraphQLArgument> args = new ArrayList<>();

        args.add(newArgument()
                .name("id")
                .type(GraphQLLong)
                .build());

        return args;
    }

    public static List<GraphQLArgument> storyArgs() {
        List<GraphQLArgument> args = new ArrayList<>();

        args.add(newArgument()
                .name("id")
                .type(GraphQLLong)
                .build());

        return args;
    }

    public static GraphQLObjectType queryType = newObject()
            .name("QueryType")
            .field(newFieldDefinition()
                    .name("recipe")
                    .type(recipeType)
                    .argument(recipeArgs())
                    .dataFetcher(FeedMeData.getRecipeDataFetcher())
                    .build())
            .field(newFieldDefinition()
                    .name("story")
                    .type(storyType)
                    .argument(storyArgs())
                    .dataFetcher(FeedMeData.getStoryDataFetcher())
                    .build())
            .build();


    public static GraphQLSchema feedMeSchema = GraphQLSchema.newSchema()
            .query(queryType)
            .build();
}

