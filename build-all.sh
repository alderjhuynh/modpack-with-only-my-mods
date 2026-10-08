#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
DEST="/Users/alderhuynh/Documents/curseforge/minecraft/Instances/modpack with only my mods/mods"
BUNDLE=""

usage() {
  echo "Usage: $(basename "$0") [--bundle[=FILE]]"
  echo "  --bundle[=FILE]  bundle built jars as a tar.gz in \$ROOT instead of moving to \$DEST"
  echo "                   (default FILE: mods-YYYYMMDD-HHMMSS.tar.gz)"
}

for arg in "$@"; do
  case "$arg" in
    --bundle)
      BUNDLE="__default__"
      ;;
    --bundle=*)
      BUNDLE="${arg#--bundle=}"
      if [[ -z "$BUNDLE" ]]; then
        echo "ERROR: --bundle= requires a file argument" >&2
        exit 1
      fi
      ;;
    -h|--help)
      usage
      exit 0
      ;;
    *)
      echo "ERROR: unknown argument: $arg" >&2
      usage >&2
      exit 1
      ;;
  esac
done

if [[ "$BUNDLE" == "__default__" ]]; then
  BUNDLE="$ROOT/mods-$(date +%Y%m%d-%H%M%S).tar.gz"
elif [[ -n "$BUNDLE" && "$BUNDLE" != /* ]]; then
  # Resolve relative bundle paths against ROOT so it lands in this directory
  BUNDLE="$ROOT/$BUNDLE"
fi

if [[ -z "$BUNDLE" ]]; then
  mkdir -p "$DEST"
else
  STAGE="$(mktemp -d "$ROOT/.bundle-stage.XXXXXX")"
  trap 'rm -rf "$STAGE"' EXIT
fi

for dir in "$ROOT"/*/; do
  if [[ ! -f "${dir}gradlew" ]]; then
    echo "Skipping $(basename "$dir"): no gradlew found"
    continue
  fi

  name="$(basename "$dir")"
  echo "=== Building $name ==="
  ( cd "$dir" && ./gradlew build )

  moved=0
  for jar in "${dir}"build/libs/*.jar; do
    [[ -e "$jar" ]] || continue
    case "$jar" in
      *-sources.jar|*-javadoc.jar) continue ;;
    esac
    if [[ -n "$BUNDLE" ]]; then
      echo "Staging $(basename "$jar") for bundle"
      cp -f "$jar" "$STAGE/"
    else
      echo "Moving $(basename "$jar") -> $DEST/"
      mv -f "$jar" "$DEST/"
    fi
    moved=1
  done

  if [[ "$moved" -eq 0 ]]; then
    echo "WARNING: no build jar found for $name" >&2
  fi
done

if [[ -n "$BUNDLE" ]]; then
  if compgen -G "$STAGE/*.jar" > /dev/null; then
    echo "Bundling staged jars -> $BUNDLE"
    tar -czf "$BUNDLE" -C "$STAGE" .
    echo "Done: $BUNDLE"
  else
    echo "WARNING: no jars staged, skipping bundle" >&2
  fi
else
  echo "Done."
fi
